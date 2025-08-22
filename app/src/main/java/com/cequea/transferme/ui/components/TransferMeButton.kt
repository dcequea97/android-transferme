package com.cequea.transferme.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cequea.transferme.ui.theme.TransferMeTheme

@Composable
fun TransferMeButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier
){
    Button(
        modifier = modifier.height(50.dp),
        onClick = onClick,
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = text,
            color = Color.White
        )
    }
}

@Composable
@Preview
private fun TransferMeButtonPreview() {
    TransferMeTheme {
        TransferMeButton(
            {},
            "Continuar"
        )
    }
}