package com.glureau.myresources.ui.string

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.glureau.myresources.R
import com.glureau.myresources.core.types.BaseResDiffCallback
import com.glureau.myresources.core.types.string.StringRes
import com.glureau.myresources.extensions.toHex

class StringAdapter : ListAdapter<StringRes, StringAdapter.ViewHolder>(
    BaseResDiffCallback()
) {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemName: TextView by lazy { itemView.findViewById<TextView>(R.id.item_string_name) }
        val itemValue: TextView by lazy { itemView.findViewById<TextView>(R.id.item_string_value) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.myr_item_string, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val res = getItem(position)
        holder.itemName.text = "${res.resName} (#${res.resId.toHex()})"
        holder.itemValue.text = "${res.value}"
    }
}