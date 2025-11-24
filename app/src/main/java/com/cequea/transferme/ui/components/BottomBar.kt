package com.cequea.transferme.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.cequea.transferme.R
import com.cequea.transferme.ui.navigation.AppScreens
import com.cequea.transferme.ui.theme.TransferMeTheme


@Composable
fun BottomBar(
    navController: androidx.navigation.NavHostController,
    modifier: Modifier = Modifier,
    currentRoute: String
) {
    fun routeOf(screen: AppScreens): String = screen::class.qualifiedName ?: ""

    val shape = RoundedCornerShape(20.dp)
    Box(
        modifier = modifier
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .clip(shape = shape)
            .dropShadow(
                shape = shape,
                block = {
                    radius = 80f
                    spread = 80f
                }
            )
            .border(
                width = 1.dp,
                color = Color.Black.copy(alpha = 0.2f),
                shape = shape
            )

            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomBarItem(
                modifier = Modifier.weight(0.25f),
                iconResource = R.drawable.home,
                isSelected = currentRoute == routeOf(AppScreens.HomeScreen),
                onClick = { navController.navigate(AppScreens.HomeScreen) }
            )

            BottomBarItem(
                modifier = Modifier.weight(0.25f),
                iconResource = R.drawable.wallet,
                isSelected = currentRoute == routeOf(AppScreens.CardDetails),
                onClick = { navController.navigate(AppScreens.CardDetails) }
            )

            BottomBarItem(
                modifier = Modifier.weight(0.25f),
                iconResource = R.drawable.chart,
                isSelected = currentRoute == routeOf(AppScreens.ChartScreen),
                onClick = { navController.navigate(AppScreens.ChartScreen) }
            )

            BottomBarItem(
                modifier = Modifier.weight(0.25f),
                iconResource = R.drawable.user_2,
                isSelected = currentRoute == routeOf(AppScreens.ProfileScreen),
                onClick = { navController.navigate(AppScreens.ProfileScreen) }
            )
        }
    }
}

@Composable
private fun BottomBarItem(
    iconResource: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxHeight()
                .width(50.dp)
                .background(
                    color = if (isSelected) Color(0xFF5164BF) else Color.Transparent,
                    shape = RoundedCornerShape(
                        topStart = 50.dp,
                        topEnd = 50.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp
                    )
                )
        )

        Icon(
            tint = if (isSelected) Color.White else Color(0xFF8EDFEB),
            painter = painterResource(id = iconResource),
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
                .clickable(
                    onClick = onClick
                )
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun BottomBarPreview() {
    TransferMeTheme {
        BottomBar(navController = rememberNavController(), currentRoute = "")
    }
}


