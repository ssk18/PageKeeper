package com.ssk.pagekeeper.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.ssk.pagekeeper.core.designsystem.R

@Composable
fun PageKeeperLogo(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.page_keeper),
        contentDescription = null,
        modifier = modifier,
    )
}
