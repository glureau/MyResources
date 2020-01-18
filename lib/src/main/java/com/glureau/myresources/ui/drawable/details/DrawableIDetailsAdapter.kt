package com.glureau.myresources.ui.drawable.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.glureau.myresources.R
import com.glureau.myresources.core.types.BaseResDiffCallback
import com.glureau.myresources.core.types.drawable.DrawableRes

class DrawableIDetailsAdapter :
    ListAdapter<DrawableRes, DrawableIDetailsAdapter.ViewHolder>(BaseResDiffCallback()) {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemText: TextView by lazy { itemView.findViewById<TextView>(R.id.item_generic_text) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.myr_item_generic, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val res = getItem(position)
        holder.itemText.text = "Package: ${res.packageName}"
    }

}