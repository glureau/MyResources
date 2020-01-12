package com.glureau.myresources.core.types.color

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import com.glureau.myresources.core.ResourceDefType
import com.glureau.myresources.core.types.BaseRes
import com.glureau.myresources.extensions.toHex

data class ColorRes(
    val _appContext: Context,
    val _resourceClassName: String,
    val _resName: String
) :
    BaseRes(_appContext, _resourceClassName, _resName, ResourceDefType.Color) {

    val defaultColor by lazy { color(appContext) ?: 0 }
    val luminance by lazy { ColorUtils.calculateLuminance(defaultColor) }
    val hsl by lazy {
        val arr = FloatArray(3)
        ColorUtils.colorToHSL(defaultColor, arr)
        arr
    }
    val hue = hsl[0]
    val saturation = hsl[1]
    val lightness = hsl[2]

    fun color(context: Context) = try {
        ContextCompat.getColor(context, resId)
    } catch (t: Throwable) {
        null
    }

    override val definitionForQuery: String
        get() = "${super.definitionForQuery} ${defaultColor.toHex()}"
}
