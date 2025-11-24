package com.cequea.transferme.domain.model

import androidx.annotation.DrawableRes
import com.cequea.transferme.R

data class Category(
    @param:DrawableRes val iconResId: Int,
    val description: String
)

fun getAllCategories(): List<Category> {
    return listOf(
        Category(iconResId = R.drawable.ic_electricity, description = "Electricity"),
        Category(iconResId = R.drawable.ic_ecommerce, description = "E-Commerce"),
        Category(iconResId = R.drawable.ic_mobile_data, description = "Mobile & Data"),
        Category(iconResId = R.drawable.ic_transportation, description = "Transportation"),
        Category(iconResId = R.drawable.ic_tv_internet, description = "TV & Internet"),
        Category(iconResId = R.drawable.ic_pharmacy, description = "Pharmacy"),
        Category(iconResId = R.drawable.ic_tickets, description = "Tickets"),
        Category(iconResId = R.drawable.ic_hotel, description = "Hotel"),
        Category(iconResId = R.drawable.ic_flight, description = "Flight"),
        Category(iconResId = R.drawable.ic_fuel, description = "Fuel"),
        Category(iconResId = R.drawable.ic_google_play, description = "Pay Google Play"),
        Category(iconResId = R.drawable.ic_food_drink, description = "Food & Drink")
    )
}

