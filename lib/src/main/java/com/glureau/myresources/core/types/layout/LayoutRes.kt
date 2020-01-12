package com.glureau.myresources.core.types.layout

import android.content.Context
import com.glureau.myresources.core.ResourceDefType
import com.glureau.myresources.core.types.BaseRes

data class LayoutRes(
    val _appContext: Context,
    val _resourceClassName: String,
    val _resName: String
) :
    BaseRes(_appContext, _resourceClassName, _resName, ResourceDefType.Layout) {
    val xml = appContext.resources.getLayout(resId)
}
