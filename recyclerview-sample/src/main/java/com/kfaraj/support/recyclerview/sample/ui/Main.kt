package com.kfaraj.support.recyclerview.sample.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.util.UUID

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Main(
    text: String,
    isChecked: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .combinedClickable(
                onClick = { onClick() },
                onLongClick = { onLongClick() }
            )
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            if (isChecked) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.TopEnd),
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Preview
@Composable
fun MainPreview() {
    Main(
        text = UUID.randomUUID().toString(),
        true,
        {},
        {}
    )
}
