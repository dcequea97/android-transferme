package com.cequea.transferme.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cequea.transferme.ui.theme.TransferMeTheme

@Composable
fun TransferMeMultilineTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color(0XFF0166FF).copy(alpha = 0.1f),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(12.dp)
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth().heightIn(min = 200.dp, max = 600.dp),
            decorationBox = { innerTextField ->
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        color = Color(0xFF5164BF),
                        fontSize = 16.sp,
                    )
                }
                innerTextField()
            }
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun TransferMeMultilineTextFieldPreview() {
    TransferMeTheme {
        TransferMeMultilineTextField(
            value = "",
            onValueChange = {},
            placeholder = "Escribe aquí tu mensaje..."
        )
    }
}