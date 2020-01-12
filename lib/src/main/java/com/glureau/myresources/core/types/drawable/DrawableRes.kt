package com.glureau.myresources.core.types.drawable

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import com.glureau.myresources.core.ResourceDefType
import com.glureau.myresources.core.types.BaseRes

data class DrawableRes(
    val _appContext: Context,
    val _resourceClassName: String,
    val _resName: String
) :
    BaseRes(_appContext, _resourceClassName, _resName, ResourceDefType.Drawable) {

    fun drawable(context: Context): Drawable? {
        return try {
            context.getDrawable(resId)
        } catch (e: Throwable) {
            Log.w("MyResources", "Cannot load drawable $resName due to ${e.message}")
            null
        }
    }

    override val definitionForQuery by lazy {
        val d = drawable(appContext)
        if (d != null)
            "${super.definitionForQuery} ${d.intrinsicWidth}x${d.intrinsicHeight} ${d::class.java.simpleName}"
        else
            "${super.definitionForQuery} null"
    }
}
