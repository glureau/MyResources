package com.glureau.myresources.core.sorter

import com.glureau.myresources.core.types.color.ColorRes

sealed class ColorSorter : ResSorter<ColorRes> {

    object HueColorSorter : ColorSorter() {
        override fun sort(item: ColorRes) = (item.hue * 1000).toInt()
    }
}