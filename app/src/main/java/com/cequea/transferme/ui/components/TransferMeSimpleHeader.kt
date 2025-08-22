package com.cequea.transferme.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cequea.transferme.ui.theme.TransferMeTheme

@Composable
fun TransferMeSimpleHeader(
    title: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center),
            color = MaterialTheme.colorScheme.onBackground
        )

        BackIconButton(
            modifier = Modifier
                .align(Alignment.CenterStart),
            onClick = onBackClick
        )
    }
}

@Composable
@Preview
private fun TransferMeSimpleHeaderPreview() {
    TransferMeTheme {
        TransferMeSimpleHeader(
            title = "Login",
            onBackClick = {}
        )
    }
}
