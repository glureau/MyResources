package com.glureau.myresources.core.types.bool

import android.content.Context
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat
import com.glureau.myresources.core.ResourceDefType
import com.glureau.myresources.core.types.BaseRes

enum class FontType(val extension: String) {
    XML("xml"),
    OTF("otf"),
    TTF("ttf"),
    OTHER("other")
}

data class FontRes(val _appContext: Context, val _resourceClassName: String, val _resName: String) :
    BaseRes(_appContext, _resourceClassName, _resName, ResourceDefType.Font) {

    val value: Typeface? = try {
        ResourcesCompat.getFont(appContext, resId)
    } catch (t: Throwable) {
        null
    }

    val resPath = appContext.getString(resId)

    val fontType = FontType.values().firstOrNull { it.extension == resPath.substringAfterLast(".") } ?: FontType.OTHER

    override val definitionForQuery: String
        get() = "${super.definitionForQuery} $value"
}