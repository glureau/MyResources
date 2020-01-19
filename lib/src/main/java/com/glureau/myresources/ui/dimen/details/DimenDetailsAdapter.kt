package com.glureau.myresources.ui.dimen.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.glureau.myresources.R
import com.glureau.myresources.core.types.BaseResDiffCallback
import com.glureau.myresources.core.types.dimen.DimenRes

class DimenDetailsAdapter :
    ListAdapter<DimenRes, DimenDetailsAdapter.ViewHolder>(BaseResDiffCallback()) {

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
        holder.itemText.text = "${res.resName} (${res.packageName})"
    }

}