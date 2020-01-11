package com.glureau.myresources.ui.bool

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.glureau.myresources.R
import com.glureau.myresources.extensions.toHex
import com.glureau.myresources.core.types.BaseResDiffCallback
import com.glureau.myresources.core.types.bool.BoolRes

class BoolAdapter : ListAdapter<BoolRes, BoolAdapter.ViewHolder>(BaseResDiffCallback()) {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemSwitch: Switch by lazy { itemView.findViewById<Switch>(R.id.item_bool_switch) }
        val itemName: TextView by lazy { itemView.findViewById<TextView>(R.id.item_bool_name) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.myr_item_bool, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val res = getItem(position)
        res.value?.let {
            holder.itemSwitch.isChecked = it
        }
        holder.itemSwitch.isEnabled = res.value != null
        holder.itemSwitch.isClickable = false

        holder.itemName.text = "${res.resName} (#${res.resId.toHex()})"
    }
}