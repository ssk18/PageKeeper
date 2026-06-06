package com.ssk.pagekeeper.feature.library.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssk.pagekeeper.core.designsystem.theme.FavoriteIcon
import com.ssk.pagekeeper.core.designsystem.theme.UnfinishIcon
import com.ssk.pagekeeper.core.designsystem.theme.LibraryIcon
import com.ssk.pagekeeper.core.designsystem.theme.PageKeeperTheme

@Composable
fun PageKeeperDrawerSheet(
    modifier: Modifier = Modifier,
    isOpen: Boolean,
    selected: DrawerDestination,
    onSelected: (DrawerDestination) -> Unit,
    onDrawerClose: () -> Unit,
    onImportClick: () -> Unit,
) {
    ModalDrawerSheet(modifier = modifier.width(DRAWER_WIDTH)) {
        Column(Modifier.verticalScroll(rememberScrollState())) {
            AnimatedMenuIcon(
                isOpen = isOpen,
                onClick = {
                    onDrawerClose()
                }
            )
            Spacer(Modifier.height(12.dp))
            ImportBookButton(
                onClick = {
                    onImportClick()
                },
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
            )
            Spacer(Modifier.height(60.dp))
            DrawerDestination.entries.forEach { destination ->
                NavigationDrawerItem(
                    icon = { Icon(destination.iconAsset(), contentDescription = null) },
                    label = { Text(destination.label) },
                    selected = destination == selected,
                    onClick = { onSelected(destination) },
                    modifier = Modifier
                        .padding(NavigationDrawerItemDefaults.ItemPadding)
                        .widthIn(max = 168.dp),
                )
            }
        }
    }
}

@Composable
fun DrawerDestination.iconAsset(): ImageVector = when (this) {
    DrawerDestination.Library -> LibraryIcon
    DrawerDestination.Favorites -> FavoriteIcon
    DrawerDestination.Finished -> UnfinishIcon
}

val DRAWER_WIDTH = 280.dp

@Preview(name = "Library selected", showBackground = true)
@Composable
private fun PageKeeperDrawerSheetLibraryPreview() {
    PageKeeperTheme {
        PageKeeperDrawerSheet(
            selected = DrawerDestination.Library,
            onSelected = {},
            isOpen = true,
            onDrawerClose = {},
            onImportClick = {},
        )
    }
}

@Preview(name = "Favorites selected", showBackground = true)
@Composable
private fun PageKeeperDrawerSheetFavoritesPreview() {
    PageKeeperTheme {
        PageKeeperDrawerSheet(
            selected = DrawerDestination.Favorites,
            onSelected = {},
            isOpen = true,
            onDrawerClose = {},
            onImportClick = {},
        )
    }
}

@Preview(name = "Finished selected", showBackground = true)
@Composable
private fun PageKeeperDrawerSheetFinishedPreview() {
    PageKeeperTheme {
        PageKeeperDrawerSheet(
            selected = DrawerDestination.Finished,
            onSelected = {},
            isOpen = true,
            onDrawerClose = {},
            onImportClick = {},
        )
    }
}
