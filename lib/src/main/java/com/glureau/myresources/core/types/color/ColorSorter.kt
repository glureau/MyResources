package com.glureau.myresources.core.types.color

import com.glureau.myresources.core.sorter.ResSorter

sealed class ColorSorter : ResSorter<ColorRes> {

    object HueColorSorter : ColorSorter() {
        override fun sort(item: ColorRes) = (item.hue * 1000).toInt()
        override fun toString() = "Hue"
    }

    object SaturationColorSorter : ColorSorter() {
        override fun sort(item: ColorRes) = (item.saturation * 1000).toInt()
        override fun toString() = "Saturation"
    }

    object LightnessColorSorter : ColorSorter() {
        override fun sort(item: ColorRes) = (item.lightness * 1000).toInt()
        override fun toString() = "Lightness"
    }

    object LuminanceColorSorter : ColorSorter() {
        override fun sort(item: ColorRes) = (item.luminance * 1000).toInt()
        override fun toString() = "Luminance"
    }
}