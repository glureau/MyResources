package com.glureau.myresources.core.types.drawable

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log

class DrawableRes(
    appContext: Context,
    packageName: String,
    private val resName: String
) {

    private val resId: Int = appContext.resources.getIdentifier(resName, "drawable", packageName)

    fun drawable(context: Context): Drawable? {
        return try {
            context.getDrawable(resId)
        } catch (e: Throwable) {
            Log.w("MyResources", "Cannot load drawable $resName due to ${e.message}")
            null
        }
    }

    fun print(context: Context): String {
        val drawable = drawable(context)
        return toString() + ": " +
                "${drawable?.intrinsicWidth}x${drawable?.intrinsicHeight} " +
                "(${if (drawable != null) drawable::class.java.simpleName else null})"
    }

    override fun toString() = "Drawable - $resName ($resId)"
}
