package com.glureau.myresources.core.types.drawable

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import com.glureau.myresources.core.ResourceDefType
import com.glureau.myresources.core.types.BaseRes

data class DrawableRes(val _appContext: Context, val _packageName: String, val _resName: String) :
    BaseRes(_appContext, _packageName, _resName, ResourceDefType.Drawable) {

    fun drawable(context: Context): Drawable? {
        return try {
            context.getDrawable(resId)
        } catch (e: Throwable) {
            Log.w("MyResources", "Cannot load drawable $resName due to ${e.message}")
            null
        }
    }

    override fun toString() = "Drawable - $resName ($resId)"
}
