package com.glureau.myresources.ui.color

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.glureau.myresources.R
import com.glureau.myresources.extensions.toHex
import com.glureau.myresources.core.types.BaseResDiffCallback
import com.glureau.myresources.core.types.color.ColorRes

class ColorAdapter : ListAdapter<ColorRes, ColorAdapter.ViewHolder>(BaseResDiffCallback()) {

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
        val res = getItem(position)
        val color = res.color(holder.itemView.context)
        color?.let {
            holder.itemColor.setImageDrawable(ColorDrawable(it))
        }

        holder.itemName.text =
            "#${color?.toHex()} ${res.resName} (id: #${res.resId.toHex()})"
    }
}