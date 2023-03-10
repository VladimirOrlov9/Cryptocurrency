package com.vladimirorlov9.cryptocurrency.utils

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

const val PRICE_DECIMAL_SIGNS = 100.0

fun Double.roundPrice() = (this * PRICE_DECIMAL_SIGNS).roundToInt() / PRICE_DECIMAL_SIGNS

fun convertMillisToDate(millis: Long): String {
    val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    val date = Date(millis)
    return sdf.format(date)
}
