package com.ssk.pagekeeper.feature.library.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.ssk.pagekeeper.core.designsystem.theme.PageKeeperTheme

@Composable
fun PageKeeperTopBar(
    isMenuOpen: Boolean,
    onNavDrawerClick: () -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AnimatedMenuIcon(
            isOpen = isMenuOpen,
            onClick = onNavDrawerClick,
        )
        Text(
            text = "Library",
            style = MaterialTheme.typography.titleMedium,
        )
        IconButton(onClick = onSearchClick) {
            Icon(
                painter = painterResource(com.ssk.pagekeeper.core.designsystem.R.drawable.ic_search),
                contentDescription = "search icon",
            )
        }
    }
}

@Preview
@Composable
private fun PageKeeperTopBarClosedPreview() {
    PageKeeperTheme {
        PageKeeperTopBar(
            isMenuOpen = false,
            onNavDrawerClick = {},
            onSearchClick = {},
        )
    }
}

@Preview
@Composable
private fun PageKeeperTopBarOpenPreview() {
    PageKeeperTheme {
        PageKeeperTopBar(
            isMenuOpen = true,
            onNavDrawerClick = {},
            onSearchClick = {},
        )
    }
}
