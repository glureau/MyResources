package com.glureau.myresources.core.types.dimen

import android.content.Context
import com.glureau.myresources.core.ResourceDefType
import com.glureau.myresources.core.types.BaseRes

data class DimenRes(val _appContext: Context, val _resourceClassName: String, val _resName: String) :
    BaseRes(_appContext, _resourceClassName, _resName, ResourceDefType.Dimen) {

    val valueString: String? = try {
        appContext.getString(resId)
    } catch (t: Throwable) {
        null
    }

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

    enum class UNIT(val definition: String) {
        DP("dp/dip (density-independent pixels)"),
        SP("sp (font size)"),
        PX("px (pixel count)"),
        PERCENT("% (percent)"),
        NONE("(no unit found)")
    }

    val rawValue: Float? = try {
        valueString?.replace(Regex("[a-zA-Z%]*"), "")?.toFloat()
    } catch (t: Throwable) {
        null
    }

    val unit: UNIT = when {
        valueString.isNullOrBlank() -> UNIT.NONE
        valueString.contains("sp") -> UNIT.SP
        valueString.contains("dip") -> UNIT.DP
        valueString.contains("px") -> UNIT.PX
        valueString.contains("%") -> UNIT.PERCENT
        else -> UNIT.NONE
    }

    override val definitionForQuery: String
        get() = "${super.definitionForQuery} $valueString $valueFloat $valuePixelSize $valuePixelOffset $rawValue $unit"
}
