package com.cequea.transferme.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.cequea.transferme.R
import com.cequea.transferme.ui.theme.TransferMeTheme

@Composable
fun PinPad(
    onKeyPress: (String) -> Unit,
    highlightedKeys: Set<String>,
    modifier: Modifier = Modifier
) {
    val keys = listOf(
        listOf("1", "2", "3"),
        listOf("4", "5", "6"),
        listOf("7", "8", "9"),
        listOf("android_face", "0", "android_fingerprint"),
    )

    Column(
        modifier = modifier.width(300.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircleProgressIndicator(5,highlightedKeys.size)

        Spacer(modifier = Modifier.height(8.dp))

        keys.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                row.forEach { key ->
                    val isHighlighted = key in highlightedKeys
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(
                                if (isHighlighted) MaterialTheme.colorScheme.primary
                                else if (key.isDigitsOnly()) MaterialTheme.colorScheme.surface
                                else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            .border(
                                width = 2.dp,
                                color = if (isHighlighted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                                shape = CircleShape
                            )
                            .clickable { onKeyPress(key) },
                        contentAlignment = Alignment.Center
                    ) {
                        if (key.isDigitsOnly()) {
                            Text(
                                text = key,
                                style = MaterialTheme.typography.titleLarge,
                                color = if (isHighlighted) Color.White else Color.Black
                            )
                        } else {
                            val iconResId = when (key) {
                                "android_face" -> R.drawable.android_face
                                "android_fingerprint" -> R.drawable.android_fingerprint
                                else -> null
                            }

                            iconResId?.let {
                                Icon(
                                    painter = painterResource(id = it),
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }

                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PinPadPreview() {
    TransferMeTheme {
        PinPad(
            onKeyPress = {},
            highlightedKeys = setOf("2", "0")
        )
    }
}