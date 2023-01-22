package com.vladimirorlov9.cryptocurrency.utils

import kotlin.math.roundToInt

const val PRICE_DECIMAL_SIGNS = 100.0

fun Double.roundPrice() = (this * PRICE_DECIMAL_SIGNS).roundToInt() / PRICE_DECIMAL_SIGNS
