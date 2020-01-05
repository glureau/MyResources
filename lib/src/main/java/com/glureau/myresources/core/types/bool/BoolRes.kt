package com.glureau.myresources.core.types.bool

import android.content.Context
import androidx.recyclerview.widget.DiffUtil
import com.glureau.myresources.core.ResourceDefType
import com.glureau.myresources.core.getIdentifier

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

    val resId: Int = appContext.getIdentifier(resName, ResourceDefType.Bool)
    val value: Boolean? = try {
        appContext.resources.getBoolean(resId)
    } catch (t: Throwable) {
        null
    }
}
