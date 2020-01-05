package com.glureau.myresources.core.types

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

class BaseResDiffCallback<T : BaseRes> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T) =
        oldItem.resName == newItem.resName

    @SuppressLint("DiffUtilEquals")// All XxxRes should be data class
    override fun areContentsTheSame(oldItem: T, newItem: T) =
        oldItem == newItem
}
