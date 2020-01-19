package com.glureau.myresources.core.types.dimen

import com.glureau.myresources.core.sorter.ResSorter

object DimenSorter : ResSorter<DimenRes> {
    private const val GROUP_SEPARATOR = 100000
    override fun sort(item: DimenRes) = when (item.unit) {
        DimenRes.UNIT.PX, DimenRes.UNIT.DP -> item.valuePixelSize ?: GROUP_SEPARATOR - 1
        DimenRes.UNIT.SP -> GROUP_SEPARATOR + (item.valuePixelSize ?: 0)
        DimenRes.UNIT.PERCENT -> 2 * GROUP_SEPARATOR + (item.rawValue?.times(100)?.toInt() ?: 0)
        DimenRes.UNIT.NONE -> 3 * GROUP_SEPARATOR + (item.rawValue?.times(100)?.toInt() ?: 0)
    }
}