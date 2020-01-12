package com.glureau.myresources.core.types.bool

import android.content.Context
import com.glureau.myresources.core.ResourceDefType
import com.glureau.myresources.core.types.BaseRes

data class BoolRes(val _appContext: Context, val _resourceClassName: String, val _resName: String) :
    BaseRes(_appContext, _resourceClassName, _resName, ResourceDefType.Bool) {

    val value: Boolean? = try {
        appContext.resources.getBoolean(resId)
    } catch (t: Throwable) {
        null
    }


    override val definitionForQuery: String
        get() = "${super.definitionForQuery} $value"
}
