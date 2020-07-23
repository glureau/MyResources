package com.glureau.myresources.core.types.string

import android.content.Context
import com.glureau.myresources.core.ResourceDefType
import com.glureau.myresources.core.types.BaseRes

data class StringRes(
    val _appContext: Context,
    val _resourceClassName: String,
    val _resName: String
) :
    BaseRes(_appContext, _resourceClassName, _resName, ResourceDefType.Strings) {

    val value = _appContext.getString(resId)

    override val definitionForQuery: String
        get() = "${super.definitionForQuery} $value"

}
