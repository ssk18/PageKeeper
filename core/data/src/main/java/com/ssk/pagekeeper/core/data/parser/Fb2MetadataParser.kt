package com.ssk.pagekeeper.core.data.parser

import android.util.Base64
import org.w3c.dom.Element
import java.io.File
import javax.inject.Inject
import javax.xml.parsers.DocumentBuilderFactory

/**
 * Extracts the small slice of FB2 metadata we need for the library: title, author, and cover.
 *
 * FB2 is namespaced XML — relevant shape:
 *
 * ```
 * <FictionBook xmlns="http://www.gribuser.ru/xml/fictionbook/2.0" xmlns:l="http://www.w3.org/1999/xlink">
 *   <description>
 *     <title-info>
 *       <author><first-name>Mark</first-name><last-name>Twain</last-name></author>
 *       <book-title>The Adventures of Tom Sawyer</book-title>
 *       <coverpage><image l:href="#cover.jpg"/></coverpage>
 *     </title-info>
 *   </description>
 *   <binary id="cover.jpg" content-type="image/jpeg">…base64…</binary>
 * </FictionBook>
 * ```
 *
 * Implementation uses DOM — simpler than XmlPullParser for the small metadata header.
 */
class Fb2MetadataParser @Inject constructor() : BookMetadataParser {
    override val supportedExtensions: Set<String> = setOf(".fb2")

    override fun parse(file: File, displayName: String): Metadata = file.inputStream().use { input ->
        val doc = DocumentBuilderFactory.newInstance().apply {
            isNamespaceAware = true
        }.newDocumentBuilder().parse(input)

        val titleInfo = doc.documentElement
            .firstChildElement("description")
            ?.firstChildElement("title-info")
            ?: return Metadata(title = null, author = null, coverBytes = null)

        val title = titleInfo.firstChildElement("book-title")?.textContent?.trim()
        val author = titleInfo.firstChildElement("author")?.let { authorEl ->
            val first = authorEl.firstChildElement("first-name")?.textContent?.trim().orEmpty()
            val last = authorEl.firstChildElement("last-name")?.textContent?.trim().orEmpty()
            listOf(first, last).filter { it.isNotEmpty() }.joinToString(" ").takeIf { it.isNotEmpty() }
        }
        val coverId = titleInfo.firstChildElement("coverpage")
            ?.firstChildElement("image")
            ?.let { it.getAttributeNS(XLINK_NAMESPACE, "href") ?: it.getAttribute("href") }
            ?.removePrefix("#")
            ?.takeIf { it.isNotEmpty() }

        val coverBytes = coverId?.let { id -> findBinary(doc.documentElement, id) }
        Metadata(title = title, author = author, coverBytes = coverBytes)
    }

    private fun findBinary(root: Element, id: String): ByteArray? {
        val binaries = root.getElementsByTagName("binary")
        for (i in 0 until binaries.length) {
            val binary = binaries.item(i) as Element
            if (binary.getAttribute("id") == id) {
                val base64 = binary.textContent.trim()
                return runCatching { Base64.decode(base64, Base64.DEFAULT) }.getOrNull()
            }
        }
        return null
    }

    private fun Element.firstChildElement(localName: String): Element? {
        val children = childNodes
        for (i in 0 until children.length) {
            val node = children.item(i)
            if (node is Element && node.localName == localName) return node
        }
        return null
    }

    private companion object {
        const val XLINK_NAMESPACE = "http://www.w3.org/1999/xlink"
    }
}
