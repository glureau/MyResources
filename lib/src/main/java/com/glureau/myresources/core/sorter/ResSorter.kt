package com.glureau.myresources.core.sorter

import com.glureau.myresources.core.types.BaseRes

interface ResSorter<T : BaseRes> {
    fun sort(item: T): Int
}

class InverseSorter<T : BaseRes>(private val sorter: ResSorter<T>) : ResSorter<T> {
    override fun sort(item: T) = -1 * sorter.sort(item)

    override fun toString() = "$sorter Desc"
}