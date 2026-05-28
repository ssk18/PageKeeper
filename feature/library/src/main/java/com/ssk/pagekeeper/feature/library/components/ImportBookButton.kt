package com.ssk.pagekeeper.feature.library.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun ImportBookButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        modifier = modifier.size(width = 168.dp, height = 56.dp),
        shape = RoundedCornerShape(16.dp),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
    ) {
        Icon(
            painter = painterResource(com.ssk.pagekeeper.core.designsystem.R.drawable.ic_import_book),
            contentDescription = null
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = "Import Book",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}