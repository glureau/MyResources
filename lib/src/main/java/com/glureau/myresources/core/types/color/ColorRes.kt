package com.glureau.myresources.core.types.color

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.recyclerview.widget.DiffUtil
import com.glureau.myresources.core.ResourceDefType
import com.glureau.myresources.core.getIdentifier

data class ColorRes(
    val appContext: Context,
    val packageName: String,
    val resName: String
) {
    object DiffCallback : DiffUtil.ItemCallback<ColorRes>() {
        override fun areItemsTheSame(oldItem: ColorRes, newItem: ColorRes) =
            oldItem.resName == newItem.resName

        override fun areContentsTheSame(oldItem: ColorRes, newItem: ColorRes) =
            oldItem == newItem

    }

    val resId: Int = appContext.getIdentifier(resName, ResourceDefType.Color)

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
}
