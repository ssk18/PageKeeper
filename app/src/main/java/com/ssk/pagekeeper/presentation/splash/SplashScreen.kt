package com.ssk.pagekeeper.presentation.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssk.pagekeeper.core.designsystem.component.PageKeeperLogo
import com.ssk.pagekeeper.core.designsystem.theme.PageKeeperTheme

@Composable
fun SplashScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center,
    ) {
        PageKeeperLogo(modifier = Modifier.size(160.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashScreenPreview() {
    PageKeeperTheme { SplashScreen() }
}
