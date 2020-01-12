package com.glureau.myresources.core.aggregator

import android.annotation.SuppressLint
import android.content.Context
import androidx.recyclerview.widget.DiffUtil
import com.glureau.myresources.core.types.BaseRes

interface ResAggregator<T : BaseRes, A : AggregatedRes<T>> {
    fun aggregate(context: Context, list: List<T>): List<A>
}

interface AggregatedRes<T : BaseRes> {
    val aggregatedBy: String
}

class AggregatedResDiffCallback<R : BaseRes, A : AggregatedRes<R>> : DiffUtil.ItemCallback<A>() {

    override fun areItemsTheSame(oldItem: A, newItem: A) =
        oldItem.aggregatedBy == newItem.aggregatedBy

    @SuppressLint("DiffUtilEquals")// All AggregatedXxxRes should be data class
    override fun areContentsTheSame(oldItem: A, newItem: A) = oldItem == newItem
}
