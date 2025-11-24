package com.cequea.transferme.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cequea.transferme.R
import com.cequea.transferme.ui.theme.TransferMeTheme

@Composable
fun SuccessScreen(
    message: String,
    onContinue: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
    ) {
        Box {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .background(
                        color = Color(0XFF8EDFEB),
                        shape = androidx.compose.foundation.shape.CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier,
                    painter = painterResource(R.drawable.check),
                    contentDescription = "Success",
                    tint = Color.White
                )
            }
        }

        Spacer(Modifier.height(40.dp))
        val textColor = Color(0XFF5166BF)
        Text(
            text = "Congrats!",
            style = MaterialTheme.typography.displayLarge,
            color = textColor,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(22.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.titleLarge,
            color = textColor
        )
        Spacer(Modifier.height(60.dp))
        TransferMeButton(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 24.dp),
            text = "Great!",
            onClick = onContinue,
        )
    }
}

@Composable
@Preview(showBackground = true)
fun SuccessScreenPreview() {
    TransferMeTheme {
        SuccessScreen(
            message = "Operation Successful!",
            onContinue = {}
        )
    }
}