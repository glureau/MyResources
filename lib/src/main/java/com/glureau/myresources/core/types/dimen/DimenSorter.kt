package com.glureau.myresources.core.types.dimen

import com.glureau.myresources.core.sorter.ResSorter
import kotlin.math.max

object DimenSorter : ResSorter<DimenRes> {
    override fun sort(item: DimenRes) = max(0, item.valuePixelSize ?: Integer.MAX_VALUE)
}