package com.glureau.myresources.core.types.color

import android.content.Context
import com.glureau.myresources.core.aggregator.AggregatedRes
import com.glureau.myresources.core.aggregator.ResAggregator
import com.glureau.myresources.extensions.toHex

data class AggregatedColorRes(
    val color: Int,
    val resources: List<ColorRes>,
    override val aggregatedBy: String = color.toHex()
) : AggregatedRes<ColorRes>

object ColorAggregator : ResAggregator<ColorRes, AggregatedColorRes> {
    override fun aggregate(context: Context, list: List<ColorRes>): List<AggregatedColorRes> {
        return list.groupBy { it.color(context) ?: it.defaultColor }
            .map { AggregatedColorRes(it.key, it.value) }
    }
}