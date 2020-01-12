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
import com.glureau.myresources.core.types.BaseResDiffCallback
import com.glureau.myresources.core.types.color.ColorRes
import com.glureau.myresources.extensions.textColorFromBackgroundColor
import com.glureau.myresources.extensions.toHex

class ColorDetailsAdapter :
    ListAdapter<ColorRes, ColorDetailsAdapter.ViewHolder>(BaseResDiffCallback<ColorRes>()) {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemColorApp: ImageView by lazy { itemView.findViewById<ImageView>(R.id.details_item_color_view_app) }
        val itemColorActivity: ImageView by lazy { itemView.findViewById<ImageView>(R.id.details_item_color_view_activity) }
        val itemColorAppHex: TextView by lazy { itemView.findViewById<TextView>(R.id.details_item_color_hex_app) }
        val itemColorActivityHex: TextView by lazy { itemView.findViewById<TextView>(R.id.details_item_color_hex_activity) }
        val itemName: TextView by lazy { itemView.findViewById<TextView>(R.id.details_item_color_name) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.myr_details_item_color, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val colorRes = getItem(position)

        holder.itemColorApp.setImageDrawable(ColorDrawable(colorRes.defaultColor))
        holder.itemColorAppHex.text = colorRes.defaultColor.toHex()
        holder.itemColorAppHex.setTextColor(colorRes.defaultColor.textColorFromBackgroundColor())

        val color = colorRes.color(holder.itemView.context) ?: Color.BLACK
        holder.itemColorActivity.setImageDrawable(ColorDrawable(color))
        holder.itemColorActivityHex.text = color.toHex()
        holder.itemColorActivityHex.setTextColor(color.textColorFromBackgroundColor())


        holder.itemName.text =
            "${colorRes.packageName}/${colorRes.resName} (id: #${colorRes.resId.toHex()})"
    }
}