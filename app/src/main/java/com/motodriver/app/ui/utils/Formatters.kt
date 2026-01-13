package com.motodriver.app.ui.utils

import java.text.NumberFormat
import java.util.Locale

/**
 * Format currency in Colombian Pesos
 */
fun formatCurrency(amount: Int): String {
    val format = NumberFormat.getCurrencyInstance(Locale("es", "CO"))
    format.maximumFractionDigits = 0
    return format.format(amount)
}

/**
 * Format distance
 */
fun formatDistance(distance: Double): String {
    return if (distance < 1) {
        "${(distance * 1000).toInt()} m"
    } else {
        String.format(Locale.US, "%.1f km", distance)
    }
}
