package com.glureau.myresources.extensions

import android.content.res.Resources
import android.graphics.Color
import androidx.core.graphics.ColorUtils

fun Int.toHex() = toUInt().toString(16).toUpperCase()

private const val KiloBytes = 1024.0
private const val MegaBytes = 1024 * 1024.0
fun Int.toHumanByteCount() =
    when {
        this > MegaBytes -> String.format("%.2f MB", (this / MegaBytes))
        this > KiloBytes -> String.format("%.2f KB", (this / KiloBytes))
        else -> String.format("%d B", this)
    }


fun Int.textColorFromBackgroundColor(): Int {
    val hsl by lazy {
        val arr = FloatArray(3)
        ColorUtils.colorToHSL(this, arr)
        arr
    }
    val lightness = hsl[2]

    return if ((Color.alpha(this) / 255f) < 0.25f) Color.BLACK else {
        if (lightness > 0.5f) Color.BLACK else Color.WHITE
    }
}

fun Int.dpToPx(): Int {
    return (this * Resources.getSystem().displayMetrics.density).toInt()
}

fun Int.pxToDp(): Int {
    return (this / Resources.getSystem().displayMetrics.density).toInt()
}