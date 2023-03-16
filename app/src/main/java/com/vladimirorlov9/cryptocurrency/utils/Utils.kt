package com.vladimirorlov9.cryptocurrency.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
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

fun saveImage(context: Context, bitmap: Bitmap, name: String) {
    context.openFileOutput(name, Context.MODE_PRIVATE).use {
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
    }
}

fun readImage(context: Context, name: String?): Bitmap? {
    var bitmap: Bitmap? = null
    try {
        context.openFileInput(name).use {
            bitmap = BitmapFactory.decodeStream(it)
        }
    } catch (ex: java.lang.Exception) {
        println(ex)
    }
    return bitmap
}

fun getBitmapFromUri(context: Context, uri: Uri): Bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
    ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, uri))
} else {
    MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
}