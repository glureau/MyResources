package com.glureau.myresources.ui.dimen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.glureau.myresources.R
import com.glureau.myresources.core.types.BaseResDiffCallback
import com.glureau.myresources.core.types.dimen.DimenRes
import com.glureau.myresources.extensions.toHex
import kotlin.math.max

class DimenAdapter : ListAdapter<DimenRes, DimenAdapter.ViewHolder>(BaseResDiffCallback()) {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val view: View by lazy { itemView.findViewById<View>(R.id.item_dimen_view) }
        val itemName: TextView by lazy { itemView.findViewById<TextView>(R.id.item_dimen_name) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.myr_item_dimen, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val res = getItem(position)
        holder.view.layoutParams = holder.view.layoutParams.apply {
            width = max(0, res.valuePixelSize ?: 0)
        }

        holder.itemName.text =
            "${res.valueFloat}/${res.valuePixelSize}/${res.valuePixelOffset} ${res.resName} (#${res.resId.toHex()})"
    }
}