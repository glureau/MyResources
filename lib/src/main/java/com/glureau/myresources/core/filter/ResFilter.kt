package com.glureau.myresources.core.filter

import com.glureau.myresources.core.types.BaseRes

interface ResFilter<T : BaseRes> {
    fun filter(res: T): Boolean
}