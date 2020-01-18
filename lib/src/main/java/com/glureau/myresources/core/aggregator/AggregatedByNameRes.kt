package com.glureau.myresources.core.aggregator

import android.content.Context
import com.glureau.myresources.core.types.BaseRes

data class AggregatedByNameRes<T : BaseRes>(
    val resources: List<T>,
    override val aggregatedBy: String
) : AggregatedRes<T>

class BaseResNameAggregator<T : BaseRes> : ResAggregator<T, AggregatedByNameRes<T>> {
    override fun aggregate(context: Context, list: List<T>): List<AggregatedByNameRes<T>> {
        return list.groupBy { it.resName }
            .map {
                AggregatedByNameRes(
                    aggregatedBy = it.key,
                    resources = it.value
                )
            }
    }
}