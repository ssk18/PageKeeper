package com.ssk.pagekeeper.core.data.parser

import org.w3c.dom.Document
import org.w3c.dom.Element
import java.io.File
import java.io.InputStream
import java.util.zip.ZipFile
import javax.inject.Inject
import javax.xml.parsers.DocumentBuilderFactory

/**
 * EPUB metadata extractor.
 *
 * An EPUB is a ZIP archive with a known structure:
 *
 *   META-INF/container.xml           → points at the OPF rootfile
 *   <opf-dir>/<package>.opf          → XML <metadata> (dc:title, dc:creator) + <manifest> items
 *   <opf-dir>/<cover-image>          → resolved via the manifest
 *
 * Cover lookup handles both EPUB 2 (`<meta name="cover" content="X"/>` plus manifest item `id="X"`)
 * and EPUB 3 (`<item properties="cover-image" .../>`).
 */
class EpubMetadataParser @Inject constructor() : BookMetadataParser {
    override val supportedExtensions: Set<String> = setOf(".epub")

    override fun parse(file: File, displayName: String): Metadata = ZipFile(file).use { zip ->
        val opfPath = findOpfPath(zip) ?: return@use Metadata(null, null, null)
        val opfDir = opfPath.substringBeforeLast('/', "")
        val opfDoc = zip.openXml(opfPath) ?: return@use Metadata(null, null, null)

        val metadataEl = opfDoc.documentElement.firstChildElement("metadata")
        val title = metadataEl?.let { findDcText(it, "title") }
        val author = metadataEl?.let { findDcText(it, "creator") }

        val manifestEl = opfDoc.documentElement.firstChildElement("manifest")
        val coverHref = manifestEl?.let { findCoverHref(metadataEl, it) }
        val coverBytes = coverHref
            ?.let { resolveZipPath(opfDir, it) }
            ?.let { path -> zip.getEntry(path)?.let { zip.getInputStream(it).use { s -> s.readBytes() } } }

        Metadata(title = title, author = author, coverBytes = coverBytes)
    }

    private fun findOpfPath(zip: ZipFile): String? {
        val containerEntry = zip.getEntry("META-INF/container.xml") ?: return null
        val containerDoc = zip.getInputStream(containerEntry).use { parseXml(it) }
        val rootfile = containerDoc.documentElement
            .firstChildElement("rootfiles")
            ?.firstChildElement("rootfile") ?: return null
        return rootfile.getAttribute("full-path").takeIf { it.isNotEmpty() }
    }

    private fun findDcText(metadata: Element, localName: String): String? {
        val children = metadata.childNodes
        for (i in 0 until children.length) {
            val node = children.item(i)
            if (node is Element && node.localName == localName && node.namespaceURI == DC_NAMESPACE) {
                return node.textContent?.trim()?.takeIf { it.isNotEmpty() }
            }
        }
        return null
    }

    private fun findCoverHref(metadata: Element?, manifest: Element): String? {
        val items = manifest.getElementsByTagName("item")
        // EPUB 3 path
        for (i in 0 until items.length) {
            val item = items.item(i) as Element
            val props = item.getAttribute("properties")
            if (props.split(' ').contains("cover-image")) {
                return item.getAttribute("href").takeIf { it.isNotEmpty() }
            }
        }
        // EPUB 2 path
        if (metadata != null) {
            val metaList = metadata.getElementsByTagName("meta")
            for (i in 0 until metaList.length) {
                val meta = metaList.item(i) as Element
                if (meta.getAttribute("name") == "cover") {
                    val coverId = meta.getAttribute("content")
                    for (j in 0 until items.length) {
                        val item = items.item(j) as Element
                        if (item.getAttribute("id") == coverId) {
                            return item.getAttribute("href").takeIf { it.isNotEmpty() }
                        }
                    }
                }
            }
        }
        return null
    }

    private fun resolveZipPath(opfDir: String, href: String): String = if (opfDir.isEmpty()) href else "$opfDir/$href"

    private fun ZipFile.openXml(path: String): Document? {
        val entry = getEntry(path) ?: return null
        return getInputStream(entry).use { parseXml(it) }
    }

    private fun parseXml(input: InputStream): Document = DocumentBuilderFactory.newInstance().apply {
        isNamespaceAware = true
    }.newDocumentBuilder().parse(input)

    private fun Element.firstChildElement(localName: String): Element? {
        val children = childNodes
        for (i in 0 until children.length) {
            val node = children.item(i)
            if (node is Element && node.localName == localName) return node
        }
        return null
    }

    private companion object {
        const val DC_NAMESPACE = "http://purl.org/dc/elements/1.1/"
    }
}
