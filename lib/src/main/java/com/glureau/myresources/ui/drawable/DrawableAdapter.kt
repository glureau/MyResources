package com.glureau.myresources.ui.drawable

import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.glureau.myresources.R
import com.glureau.myresources.core.aggregator.AggregatedByNameRes
import com.glureau.myresources.core.aggregator.AggregatedResDiffCallback
import com.glureau.myresources.core.types.drawable.DrawableRes
import com.glureau.myresources.extensions.setupTransparentBackground
import com.glureau.myresources.extensions.startAndRepeatAnimations
import com.glureau.myresources.extensions.toHex
import com.glureau.myresources.extensions.toHumanByteCount

class DrawableAdapter(
    private val listener: AdapterListener
) : ListAdapter<AggregatedByNameRes<DrawableRes>, DrawableAdapter.ViewHolder>(
    AggregatedResDiffCallback<DrawableRes, AggregatedByNameRes<DrawableRes>>()
) {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemDrawableView: ImageView by lazy { itemView.findViewById<ImageView>(R.id.item_drawable_view) }
        val itemDrawableName: TextView by lazy { itemView.findViewById<TextView>(R.id.item_drawable_name) }
        val itemDrawableSize: TextView by lazy { itemView.findViewById<TextView>(R.id.item_drawable_size) }
        val itemDrawableDimension: TextView by lazy { itemView.findViewById<TextView>(R.id.item_drawable_dimension) }
    }

    interface AdapterListener {
        fun onClick(resName: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.myr_item_drawable, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val agg = getItem(position)
        val res = agg.resources.first()
        holder.itemDrawableView.setImageResource(res.resId)
        holder.itemDrawableView.setupTransparentBackground()
        holder.itemDrawableName.text =
            "${res.resName} " + if (agg.resources.isNotEmpty()) "(${agg.resources.count()} refs)" else "(#${res.resId.toHex()})"

        val drawable = holder.itemDrawableView.drawable
        holder.itemDrawableSize.text =
            "Size: " + (if (drawable is BitmapDrawable) drawable.bitmap.allocationByteCount.toHumanByteCount() else "N/A (not a bitmap)")
        if (drawable != null) {
            holder.itemDrawableDimension.text =
                drawable::class.java.simpleName + " (" + drawable.intrinsicWidth + "x" + drawable.intrinsicHeight + ")"

            holder.itemDrawableView.startAndRepeatAnimations()
        } else {
            holder.itemDrawableDimension.text = "(no drawable)"
        }
        holder.itemView.setOnClickListener {
            listener.onClick(agg.aggregatedBy)
        }
    }
}