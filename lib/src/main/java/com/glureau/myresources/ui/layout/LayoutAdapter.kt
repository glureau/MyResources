package com.glureau.myresources.ui.layout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.glureau.myresources.R
import com.glureau.myresources.core.types.BaseResDiffCallback
import com.glureau.myresources.core.types.layout.LayoutRes

class LayoutAdapter(private val listener: LayoutAdapterListener) :
    ListAdapter<LayoutRes, LayoutAdapter.ViewHolder>(BaseResDiffCallback()) {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemName: TextView by lazy { itemView.findViewById<TextView>(R.id.item_layout_name) }
    }

    interface LayoutAdapterListener {
        fun onClick(layout: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.myr_item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val res = getItem(position)
        holder.itemName.text = "Open ${res.resName} (${res.resId})"
        holder.itemView.setOnClickListener {
            listener.onClick(res.resId)
        }
    }
}