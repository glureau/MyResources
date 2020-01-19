package com.glureau.myresources.ui.dimen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.glureau.myresources.R
import com.glureau.myresources.core.aggregator.AggregatedResDiffCallback
import com.glureau.myresources.core.types.dimen.AggregatedDimenRes
import com.glureau.myresources.core.types.dimen.DimenRes
import com.glureau.myresources.extensions.dpToPx
import com.glureau.myresources.ui.drawable.DrawableAdapter
import kotlin.math.abs

class DimenAdapter(
    private val listener: DrawableAdapter.AdapterListener
) : ListAdapter<AggregatedDimenRes, DimenAdapter.ViewHolder>(AggregatedResDiffCallback()) {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val view: View by lazy { itemView.findViewById<View>(R.id.item_dimen_view) }
        val itemName: TextView by lazy { itemView.findViewById<TextView>(R.id.item_dimen_name) }
    }

    interface AdapterListener {
        fun onClick(valueString: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.myr_item_dimen, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val agg = getItem(position)
        val res = agg.resources.first()

        holder.view.layoutParams = holder.view.layoutParams.apply {
            width = when (res.unit) {
                DimenRes.UNIT.PX, DimenRes.UNIT.DP, DimenRes.UNIT.NONE -> abs(res.valuePixelSize ?: 0)
                DimenRes.UNIT.PERCENT -> ((res.rawValue ?: 0f) / 100f * holder.itemView.width).toInt()
                else -> 0
            }
            if (this is ViewGroup.MarginLayoutParams) {
                marginStart = if (res.unit == DimenRes.UNIT.PERCENT) 0 else 4.dpToPx()
            }
        }

        if (res.unit == DimenRes.UNIT.SP) {
            holder.itemName.textSize = res.rawValue ?: 14f
        } else {
            holder.itemName.textSize = 14f //14dp = Std default Android text size
        }

        val bgColor = if (res.valuePixelSize ?: 0 < 0) R.color.colorAccent else R.color.colorPrimary
        holder.view.setBackgroundColor(ContextCompat.getColor(holder.view.context, bgColor))

        val onlyOneName = agg.resources.map { it.resName }.distinct().count() == 1
        holder.itemName.text = res.valueString +
                if (onlyOneName) {
                    " ${res.resName}"
                } else {
                    ""
                } +
                " (${agg.resources.count()} refs)"


        holder.itemView.setOnClickListener {
            listener.onClick(agg.aggregatedBy)
        }
    }
}