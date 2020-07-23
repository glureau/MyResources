package com.glureau.myresources.core.types.string

import android.content.Context
import com.glureau.myresources.core.ResourceDefType
import com.glureau.myresources.core.types.BaseRes

data class StringRes(
    private val _appContext: Context,
    val _resourceClassName: String,
    val _resName: String
) : BaseRes(_appContext, _resourceClassName, _resName, ResourceDefType.Strings) {

    val value: String = try {
        _appContext.getString(resId)
    } catch (t: Throwable) {
        "⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️\n" +
                "⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️\n" +
                "Cannot load String '$_resName' because ${t.message}\n" +
                "⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️\n" +
                "⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️\n"
    }

    override val definitionForQuery: String
        get() = "${super.definitionForQuery} ${if (value.isEmpty()) "EMPTY" else value}"
}
