package com.glureau.myresources.ui.drawable

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.glureau.myresources.R
import com.glureau.myresources.extensions.toHex
import com.glureau.myresources.extensions.toHumanByteCount
import com.glureau.myresources.core.types.BaseResDiffCallback
import com.glureau.myresources.core.types.drawable.DrawableRes

class DrawableAdapter :
    ListAdapter<DrawableRes, DrawableAdapter.ViewHolder>(BaseResDiffCallback()) {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemDrawableView: ImageView by lazy { itemView.findViewById<ImageView>(R.id.item_drawable_view) }
        val itemDrawableName: TextView by lazy { itemView.findViewById<TextView>(R.id.item_drawable_name) }
        val itemDrawableSize: TextView by lazy { itemView.findViewById<TextView>(R.id.item_drawable_size) }
        val itemDrawableDimension: TextView by lazy { itemView.findViewById<TextView>(R.id.item_drawable_dimension) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.myr_item_drawable, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val res = getItem(position)
        holder.itemDrawableView.setImageResource(res.resId)
        holder.itemDrawableName.text = "${res.resName} (#${res.resId.toHex()})"
        val drawable: Drawable? = holder.itemDrawableView.drawable
        holder.itemDrawableSize.text =
            "Size: " + (if (drawable is BitmapDrawable) drawable.bitmap.allocationByteCount.toHumanByteCount() else "N/A (not a bitmap)")
        if (drawable != null) {
            holder.itemDrawableDimension.text =
                drawable::class.java.simpleName + " (" + drawable.intrinsicWidth + "x" + drawable.intrinsicHeight + ")"
        } else {
            holder.itemDrawableDimension.text = "(no drawable)"
        }
    }

}