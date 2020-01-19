package com.glureau.myresources.ui.font

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.glureau.myresources.R
import com.glureau.myresources.core.types.BaseResDiffCallback
import com.glureau.myresources.core.types.bool.FontRes
import com.glureau.myresources.extensions.toHex

class FontAdapter(private val listener: FontAdapterListener) :
    ListAdapter<FontRes, FontAdapter.ViewHolder>(BaseResDiffCallback()) {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView by lazy { itemView.findViewById<TextView>(R.id.item_font_text) }
    }

    interface FontAdapterListener {
        fun onClick(font: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.myr_item_font, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val res = getItem(position)
        holder.textView.text = "${res.resName} " +
                (if (res.value?.isBold == true) "(is bold) " else "") +
                (if (res.value?.isItalic == true) "(is italic) " else "") +
                res.fontType +
                " (#${res.resId.toHex()})"
        holder.textView.setTypeface(res.value, Typeface.NORMAL)

        holder.itemView.setOnClickListener {
            listener.onClick(res.resId)
        }
    }
}