package com.glureau.myresources.ui.color

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.glureau.myresources.R
import com.glureau.myresources.core.toHex
import com.glureau.myresources.core.types.color.ColorRes

class ColorAdapter : ListAdapter<ColorRes, ColorAdapter.ViewHolder>(ColorRes.DiffCallback) {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemColor: View by lazy { itemView.findViewById<View>(R.id.item_color_view) }
        val itemName: TextView by lazy { itemView.findViewById<TextView>(R.id.item_color_name) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_color, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val res = getItem(position)
        val color = res.color(holder.itemView.context)
        color?.let {
            holder.itemColor.setBackgroundColor(it)
        }

        holder.itemName.text =
            "#${color?.toHex()} ${res.resName} (id: #${res.resId.toHex()})"
    }
}