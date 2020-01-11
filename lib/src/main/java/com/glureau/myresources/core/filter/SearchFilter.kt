package com.glureau.myresources.core.filter

import com.glureau.myresources.core.types.BaseRes

class SearchFilter : ResFilter<BaseRes> {
    var query: String? = null
    override fun filter(res: BaseRes) = query.isNullOrEmpty() || res.toString().contains(query!!)
}