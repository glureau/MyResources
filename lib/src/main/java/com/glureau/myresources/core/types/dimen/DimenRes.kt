package com.glureau.myresources.core.types.dimen

import android.content.Context
import com.glureau.myresources.core.ResourceDefType
import com.glureau.myresources.core.types.BaseRes

data class DimenRes(val _appContext: Context, val _resourceClassName: String, val _resName: String) :
    BaseRes(_appContext, _resourceClassName, _resName, ResourceDefType.Dimen) {

    val valueFloat: Float? = try {
        appContext.resources.getDimension(resId)
    } catch (t: Throwable) {
        null
    }

    val valuePixelSize: Int? = try {
        appContext.resources.getDimensionPixelSize(resId)
    } catch (t: Throwable) {
        null
    }

    val valuePixelOffset: Int? = try {
        appContext.resources.getDimensionPixelOffset(resId)
    } catch (t: Throwable) {
        null
    }

    override val definitionForQuery: String
        get() = "${super.definitionForQuery} $valueFloat $valuePixelSize $valuePixelOffset"
}
