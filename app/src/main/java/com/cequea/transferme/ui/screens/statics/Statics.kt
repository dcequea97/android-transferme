package com.cequea.transferme.ui.screens.statics

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.cequea.transferme.R
import com.cequea.transferme.ui.components.TransferMeCustomList
import com.cequea.transferme.ui.components.TransferMeSimpleHeader
import com.cequea.transferme.ui.theme.TransferMeTheme
import com.cequea.transferme.utils.Constants
import org.koin.androidx.compose.koinViewModel

@Composable
fun StaticsRoot(
    navController: NavController,
    viewModel: StaticsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    StaticsScreen(
        state = state,
        onAction = viewModel::onAction,
        navController = navController
    )
}

@Composable
private fun StaticsScreen(
    state: StaticsState,
    onAction: (StaticsAction) -> Unit,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Constants.HORIZONTAL_PADDING.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TransferMeSimpleHeader(
            modifier = Modifier.padding(vertical = Constants.HORIZONTAL_PADDING.dp),
            title = "Activity",
            onBackClick = { navController.popBackStack() }
        )

        Spacer(Modifier.height(16.dp))
        TransferMeCustomList(
            modifier = Modifier.padding(horizontal = Constants.HORIZONTAL_PADDING.dp),
            title = "Overall Incomings",
            onSeeAllClicked = { /* TODO */ }
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(),
//                    )
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                tonalElevation = 4.dp,
                shadowElevation = 4.dp,
            ) {
                Column {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = Color(0XFF5264BE),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(8.dp)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.calendar),
                                    contentDescription = "Calendar Icon",
                                    tint = Color.White,
                                )
                            }

                            Text(
                                modifier = Modifier
                                    .padding(start = 12.dp)
                                    .align(Alignment.CenterVertically),
                                text = "Last 30 days",
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                        Icon(
                            painter = painterResource(R.drawable.chart),
                            contentDescription = "Chart Icon",
                            tint = Color.Unspecified,
                        )
                    }
                    LineChart(
                        data = state.incomingChartData,
                        highlightIndex = 3,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }
        }

        TransferMeCustomList(
            modifier = Modifier.padding(
                horizontal = Constants.HORIZONTAL_PADDING.dp,
                vertical = 24.dp
            ),
            title = "Overall Outgoings",
            onSeeAllClicked = { /* TODO */ }
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(),
//                    )
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                tonalElevation = 4.dp,
                shadowElevation = 4.dp,
            ) {
                Column {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = Color(0XFF8CD9E9),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(8.dp)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.calendar),
                                    contentDescription = "Calendar Icon",
                                    tint = Color.White,
                                )
                            }

                            Text(
                                modifier = Modifier
                                    .padding(start = 12.dp)
                                    .align(Alignment.CenterVertically),
                                text = "Last 30 days",
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                        Icon(
                            painter = painterResource(R.drawable.chart),
                            contentDescription = "Chart Icon",
                            tint = Color(0XFF5264BE),
                        )
                    }
                    LineChart(
                        data = state.outgoingChartData,
                        highlightIndex = 3,
                        modifier = Modifier
                            .fillMaxWidth(),
                        lineColor = Color(0xFFFF3E24),
                        fillColor = Color(0xFF5264BE),
                        gridColor = Color(0xFF5264BE),
                    )
                }
            }
        }
    }
}

private fun catmullRomClampedToCubic(points: List<Offset>, path: Path, tension: Float = 0.5f) {
    if (points.size < 2) return
    path.moveTo(points.first().x, points.first().y)

    for (i in 0 until points.size - 1) {
        val p0 = points[maxOf(i - 1, 0)]
        val p1 = points[i]
        val p2 = points[i + 1]
        val p3 = points[minOf(i + 2, points.lastIndex)]

        val t = tension
        var cp1 = Offset(
            x = p1.x + (p2.x - p0.x) * t / 6f,
            y = p1.y + (p2.y - p0.y) * t / 6f
        )
        var cp2 = Offset(
            x = p2.x - (p3.x - p1.x) * t / 6f,
            y = p2.y - (p3.y - p1.y) * t / 6f
        )

        // Clamp Y to local segment range to avoid overshoot
        val minY = minOf(p1.y, p2.y)
        val maxY = maxOf(p1.y, p2.y)
        cp1 = cp1.copy(y = cp1.y.coerceIn(minY, maxY))
        cp2 = cp2.copy(y = cp2.y.coerceIn(minY, maxY))

        path.cubicTo(cp1.x, cp1.y, cp2.x, cp2.y, p2.x, p2.y)
    }
}


private fun catmullRomToCubic(points: List<Offset>, path: Path, tension: Float = 0.5f) {
    if (points.size < 2) return
    path.moveTo(points.first().x, points.first().y)
    for (i in 0 until points.size - 1) {
        val p0 = points[maxOf(i - 1, 0)]
        val p1 = points[i]
        val p2 = points[i + 1]
        val p3 = points[minOf(i + 2, points.lastIndex)]

        val t = tension
        val cp1 = Offset(
            x = p1.x + (p2.x - p0.x) * t / 6f,
            y = p1.y + (p2.y - p0.y) * t / 6f
        )
        val cp2 = Offset(
            x = p2.x - (p3.x - p1.x) * t / 6f,
            y = p2.y - (p3.y - p1.y) * t / 6f
        )

        path.cubicTo(cp1.x, cp1.y, cp2.x, cp2.y, p2.x, p2.y)
    }
}

@Composable
fun LineChart(
    data: List<DataPoint>,
    modifier: Modifier = Modifier,
    highlightIndex: Int = 3,
    lineColor: Color = Color(0xFF8BD9E9),
    fillColor: Color = Color(0xFF8BD9E9),
    gridColor: Color = Color(0xFF8BD9E9),
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(225.dp)
    ) {
        if (data.isEmpty()) return@Canvas

        // Layout paddings internos para evitar cortes
        val leftPadding = 0f
        val rightPadding = 0f
        val topPadding = 16f
        val bottomPadding = 0f

        val chartWidth = size.width - leftPadding - rightPadding
        val chartHeight = size.height - topPadding - bottomPadding

        // Escalas
        val minY = 0f
        val maxY = data.maxOf { it.value }.coerceAtLeast(1f) // evita rango 0
        val yRange = maxY - minY

        val stepX = chartWidth / (data.size - 1).coerceAtLeast(1)
        fun toPoint(i: Int): Offset {
            val x = leftPadding + i * stepX
            val normalized = (data[i].value - minY) / yRange
            val y = topPadding + (1f - normalized) * chartHeight
            return Offset(x, y)
        }

        // Puntos
        val points = List(data.size) { toPoint(it) }

        // Grid horizontal leve
        val gridLines = 3
        repeat(gridLines) { idx ->
            val y = topPadding + (idx + 1) * chartHeight / (gridLines + 1)
            drawLine(
                color = gridColor,
                start = Offset(leftPadding, y),
                end = Offset(size.width - rightPadding, y),
                strokeWidth = 1f
            )
        }

        // Path suave para línea
        val linePath = Path().apply { catmullRomClampedToCubic(points, this, tension = 0.6f) }

        // Área bajo la línea
        val fillPath = Path().apply {
            addPath(linePath)
            // cerrar hacia abajo
            lineTo(points.last().x, size.height - bottomPadding)
            lineTo(points.first().x, size.height - bottomPadding)
            close()
        }

        val bounds = fillPath.getBounds()
        val gradient = Brush.verticalGradient(
            colors = listOf(
                fillColor,
                fillColor,
                fillColor,
                fillColor.copy(alpha = 0f)
            ),
            startY = bounds.top,
            endY = bounds.bottom
        )
        drawPath(path = fillPath, brush = gradient)

        // Línea principal
        drawPath(
            path = linePath,
            color = lineColor,
            style = Stroke(width = 4f, cap = StrokeCap.Round)
        )

        // Punto destacado
        val hi = highlightIndex.coerceIn(0, points.lastIndex)
        val hp = points[hi]
        drawCircle(color = Color.White, radius = 8f, center = hp)
        drawCircle(color = lineColor, radius = 6f, center = hp)

        // Flecha hacia abajo y label ($50.84)
//            val arrowLen = 12f
//            drawLine(
//                color = lineColor,
//                start = Offset(hp.x, hp.y - 16f),
//                end = Offset(hp.x, hp.y - 16f - arrowLen),
//                strokeWidth = 3f,
//                cap = StrokeCap.Round
//            )
    }
}

data class DataPoint(val xLabel: String, val value: Float)


@Composable
@Preview(showBackground = true)
private fun StaticsScreenPreview() {
    TransferMeTheme {
        StaticsScreen(
            state = StaticsState(),
            onAction = {},
            navController = rememberNavController()
        )
    }
}