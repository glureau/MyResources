package com.glureau.myresources.core.types.drawable

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.recyclerview.widget.DiffUtil

class DrawableRes(
    appContext: Context,
    packageName: String,
    val resName: String
) {
    object DiffCallback : DiffUtil.ItemCallback<DrawableRes>() {
        override fun areItemsTheSame(oldItem: DrawableRes, newItem: DrawableRes) =
            oldItem.resName == newItem.resName

        override fun areContentsTheSame(oldItem: DrawableRes, newItem: DrawableRes) =
            oldItem == newItem

    }

    val resId: Int = appContext.resources.getIdentifier(resName, "drawable", packageName)

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
    override fun hashCode() = resId
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DrawableRes

        if (resId != other.resId) return false

        return true
    }

}
