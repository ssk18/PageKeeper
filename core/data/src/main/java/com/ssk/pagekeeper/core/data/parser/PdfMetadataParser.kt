package com.ssk.pagekeeper.core.data.parser

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import java.io.ByteArrayOutputStream
import java.io.File
import javax.inject.Inject

/**
 * PDF cover-only parser. Android's stock [PdfRenderer] doesn't expose the document's
 * Info / XMP dictionary, so we fall back to the file's display name for the title and
 * leave the author null — repository fills in "Unknown author."
 *
 * Cover: render the first page to a Bitmap at 2× resolution and re-encode as JPEG.
 */
class PdfMetadataParser @Inject constructor() : BookMetadataParser {
    override val supportedExtensions: Set<String> = setOf(".pdf")

    override fun parse(file: File, displayName: String): Metadata {
        val coverBytes = renderFirstPageAsJpeg(file)
        return Metadata(title = null, author = null, coverBytes = coverBytes)
    }

    private fun renderFirstPageAsJpeg(file: File): ByteArray? = runCatching {
        ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY).use { pfd ->
            PdfRenderer(pfd).use { renderer ->
                if (renderer.pageCount == 0) return@runCatching null
                renderer.openPage(0).use { page ->
                    val width = page.width * RENDER_SCALE
                    val height = page.height * RENDER_SCALE
                    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                    page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                    val out = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, JPEG_QUALITY, out)
                    bitmap.recycle()
                    out.toByteArray()
                }
            }
        }
    }.getOrNull()

    private companion object {
        const val RENDER_SCALE = 2
        const val JPEG_QUALITY = 90
    }
}
