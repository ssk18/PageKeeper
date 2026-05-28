package com.ssk.pagekeeper.feature.library.components

/**
 * Top-level destinations the navigation drawer exposes. Library is the only one with
 * a Nav3 route today — Favorites and Finished are placeholders until those screens land.
 *
 * Icons are resolved inside the drawer sheet via a `@Composable` mapper (the underlying
 * vectors are loaded via `vectorResource`, which requires a Composable context, so we
 * can't store `ImageVector` instances directly on the enum).
 */
enum class DrawerDestination(val label: String) {
    Library("Library"),
    Favorites("Favorites"),
    Finished("Finished"),
}
