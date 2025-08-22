package com.cequea.transferme.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cequea.transferme.R
import com.cequea.transferme.ui.theme.TransferMeTheme

@Composable
fun BackIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box (
        modifier = modifier
            .height(35.dp)
            .width(65.dp)
            .background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(16.dp))
            .clickable(
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.width(24.dp),
            painter = painterResource(id = R.drawable.arrow_to_top),
            contentDescription = "Back",
        )
    }
}

@Composable
@Preview
private fun BackIconButtonPreview() {
    TransferMeTheme {
        BackIconButton(
            onClick = {}
        )
    }
}