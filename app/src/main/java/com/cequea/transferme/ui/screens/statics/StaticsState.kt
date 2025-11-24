package com.cequea.transferme.ui.screens.statics

data class StaticsState(
    val isLoading: Boolean = false,
    val incomingChartData: List<DataPoint> = listOf(
        DataPoint("09 May", 30f),
        DataPoint("10 May", 40f),
        DataPoint("11 May", 45f),
        DataPoint("13 May", 90f),
        DataPoint("14 May", 30f),
        DataPoint("15 May", 32f),
        DataPoint("16 May", 50f),
        DataPoint("17 May", 55f),
        DataPoint("18 May", 59f),
        DataPoint("19 May", 70f),
        DataPoint("20 May", 80f),
        DataPoint("21 May", 55f),
    ),
    val outgoingChartData: List<DataPoint> = listOf(
        DataPoint("09 May", 20f),
        DataPoint("10 May", 30f),
        DataPoint("11 May", 25f),
        DataPoint("13 May", 60f),
        DataPoint("14 May", 20f),
        DataPoint("15 May", 22f),
        DataPoint("16 May", 40f),
        DataPoint("17 May", 35f),
        DataPoint("18 May", 49f),
        DataPoint("19 May", 50f),
        DataPoint("20 May", 60f),
        DataPoint("21 May", 45f),
    )
)
