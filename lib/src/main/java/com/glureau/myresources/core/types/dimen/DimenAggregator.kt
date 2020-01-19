package com.glureau.myresources.core.types.dimen

import android.content.Context
import com.glureau.myresources.core.aggregator.AggregatedRes
import com.glureau.myresources.core.aggregator.ResAggregator

data class AggregatedDimenRes(
    val dimenStr: String,
    val resources: List<DimenRes>,
    override val aggregatedBy: String = dimenStr
) : AggregatedRes<DimenRes>

object DimenAggregator : ResAggregator<DimenRes, AggregatedDimenRes> {
    override fun aggregate(context: Context, list: List<DimenRes>): List<AggregatedDimenRes> {
        return list.groupBy { it.valueString }
            .map { AggregatedDimenRes(it.key ?: "error", it.value) }
    }
}