package com.cequea.transferme

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.cequea.transferme.ui.navigation.AppNavigation
import com.cequea.transferme.ui.theme.TransferMeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().apply {
            setKeepOnScreenCondition { false }
            setOnExitAnimationListener { screen ->
                applySystemBars()

                ObjectAnimator.ofFloat(screen.iconView, View.SCALE_X, 0.4f, 0f)
                    .apply {
                        interpolator = OvershootInterpolator()
                        duration = 500L
                        doOnEnd { screen.remove() }
                        start()
                    }

                ObjectAnimator.ofFloat(screen.iconView, View.SCALE_Y, 0.4f, 0f)
                    .apply {
                        interpolator = OvershootInterpolator()
                        duration = 500L
                        doOnEnd { screen.remove() }
                        start()
                    }
            }
        }

        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        applySystemBars()

        setContent {
            TransferMeTheme {
                GradientSystemBarsBackground {
                    AppNavigation()
                }
            }
        }
    }


    private fun applySystemBars() {
        val transparent = Color.Transparent.toArgb()
        enableEdgeToEdge(
            SystemBarStyle.light(transparent, transparent),
            SystemBarStyle.light(transparent, transparent)
        )
    }
}


@Composable
fun GradientSystemBarsBackground(
    modifier: Modifier = Modifier,
    colors: List<Color> = listOf(
        MaterialTheme.colorScheme.primary,
        Color.White
    ),
    content: @Composable () -> Unit
) {
    val topHeight = WindowInsets.statusBars
        .asPaddingValues()
        .calculateTopPadding()

    Box(modifier = modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(topHeight + 20.dp)
                .background(
                    brush = Brush.verticalGradient(colors = colors)
                )
                .zIndex(1000f)
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            content()
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    TransferMeTheme {
        GradientSystemBarsBackground {
            Text("Content")
        }
    }
}