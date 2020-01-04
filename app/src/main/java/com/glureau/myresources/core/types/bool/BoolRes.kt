package com.glureau.myresources.core.types.bool

import android.content.Context
import androidx.recyclerview.widget.DiffUtil

data class BoolRes(
    val appContext: Context,
    val packageName: String,
    val resName: String
) {
    object DiffCallback : DiffUtil.ItemCallback<BoolRes>() {
        override fun areItemsTheSame(oldItem: BoolRes, newItem: BoolRes) =
            oldItem.resName == newItem.resName

        override fun areContentsTheSame(oldItem: BoolRes, newItem: BoolRes) =
            oldItem == newItem

    }

    val resId: Int = appContext.resources.getIdentifier(resName, "bool", packageName)
    val value: Boolean? = try {
        appContext.resources.getBoolean(resId)
    } catch (t: Throwable) {
        null
    }
}
