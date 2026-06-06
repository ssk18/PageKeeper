package com.ssk.pagekeeper.feature.library

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ssk.pagekeeper.core.designsystem.theme.DeleteIcon
import com.ssk.pagekeeper.core.designsystem.theme.FavoriteIcon
import com.ssk.pagekeeper.core.designsystem.theme.FinishIcon
import com.ssk.pagekeeper.core.designsystem.theme.UnfinishIcon
import com.ssk.pagekeeper.core.designsystem.theme.PageKeeperTheme
import com.ssk.pagekeeper.core.designsystem.theme.ShareIcon
import com.ssk.pagekeeper.core.domain.model.Book
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Composable
fun BookCard(
    book: Book,
    isFinished: Boolean,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
    ) {
        Row(
            modifier = Modifier
                .padding(start = 12.dp, top = 12.dp, bottom = 12.dp)
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.Top,
        ) {
            BookCover(
                coverPath = book.coverPath,
                modifier = Modifier.size(width = 104.dp, height = 156.dp)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(
                    modifier = Modifier.padding(end = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Text(
                        book.title,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        book.author,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Row(modifier = Modifier.fillMaxWidth()) {
                    IconButton(onClick = {}) { Icon(FavoriteIcon, contentDescription = "Favorite") }
                    IconButton(onClick = {}) {
                        Icon(
                            if (isFinished) FinishIcon else UnfinishIcon,
                            contentDescription = if (isFinished) "Mark unfinished" else "Mark finished"
                        )
                    }
                    IconButton(onClick = {}) { Icon(ShareIcon, contentDescription = "Share") }
                    Spacer(Modifier.weight(1f))
                    IconButton(onClick = {}) { Icon(DeleteIcon, contentDescription = "Delete") }
                }
            }
        }
    }
}

@Composable
private fun BookCover(coverPath: String?, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .aspectRatio(2f / 3f)
            .clip(RoundedCornerShape(6.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant),
    ) {
        if (coverPath != null) {
            AsyncImage(
                model = coverPath,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@OptIn(ExperimentalTime::class)
@Preview(showBackground = true, widthDp = 360)
@Composable
private fun BookCardPreview() {
    PageKeeperTheme {
        BookCard(
            book = Book(
                id = "preview",
                title = "The Adventures of Tom Sawyer",
                author = "Mark Twain",
                coverPath = null,
                filePath = "/tmp/preview.fb2",
                dateAdded = Clock.System.now(),
            ),
            isFinished = false,
            modifier = Modifier.padding(16.dp),
        )
    }
}
