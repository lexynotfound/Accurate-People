package com.rai.accuratepeople.core.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SortToggleButton(
    isAscending: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(onClick = onToggle, modifier = modifier) {
        Icon(
            imageVector = if (isAscending) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward,
            contentDescription = if (isAscending) "Sort A-Z" else "Sort Z-A",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}
