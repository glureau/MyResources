package com.glureau.myresources.ui.color

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.glureau.myresources.R
import com.glureau.myresources.core.aggregator.AggregatedResDiffCallback
import com.glureau.myresources.core.types.color.AggregatedColorRes
import com.glureau.myresources.core.types.color.ColorRes
import com.glureau.myresources.extensions.toHex

class AggregatedColorAdapter : ListAdapter<AggregatedColorRes,
        AggregatedColorAdapter.ViewHolder>(AggregatedResDiffCallback<ColorRes, AggregatedColorRes>()) {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemColor: ImageView by lazy { itemView.findViewById<ImageView>(R.id.item_color_view) }
        val itemName: TextView by lazy { itemView.findViewById<TextView>(R.id.item_color_name) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.myr_item_color, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val agg = getItem(position)
        val color = agg.resources.first().color(holder.itemView.context) ?: Color.BLACK
        holder.itemColor.setImageDrawable(ColorDrawable(color))

        holder.itemName.text =
            "#${color.toHex()} ${agg.resources.joinToString { it.resName }} (id: #${agg.resources.joinToString { it.resId.toHex() }})"
    }
}