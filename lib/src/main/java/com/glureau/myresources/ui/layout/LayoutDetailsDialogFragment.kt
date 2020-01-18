package com.glureau.myresources.ui.layout

import android.content.res.XmlResourceParser
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.glureau.myresources.R
import com.glureau.myresources.core.ResParser
import com.glureau.myresources.extensions.filterViews
import com.glureau.myresources.extensions.setupTransparentBackground

class LayoutDetailsDialogFragment : DialogFragment() {

    companion object {
        const val TAG = "LayoutDetailsDialogFragment"
        private const val ARG_LAYOUT = "ARG_LAYOUT"
        fun newInstance(layout: Int) = LayoutDetailsDialogFragment().apply {
            arguments = bundleOf(
                ARG_LAYOUT to layout
            )
        }
    }

    private val layout by lazy { requireArguments().getInt(ARG_LAYOUT) }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val res = ResParser.repository.getLayouts().first { it.resId == layout }
        val xml = res.xml
        logXml(xml)
        xml.next()
        logXml(xml)
        try {
            return inflater.inflate(layout, container, false)
        } catch (t: Throwable) {
            Log.e("MyResources", "Cannot open ${t.message}")
        }
        return null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setupTransparentBackground()
        view.filterViews<TextView>().forEach {
            it.text = "Lorem Ipsum"
        }
        view.filterViews<ImageView>().forEach {
            it.setImageResource(R.drawable.myr_ic_image_white_24dp)
        }
    }

    private fun logXml(xml: XmlResourceParser) {
        Log.e("MyResources", "xml.name=${xml.name}")
        Log.e("MyResources", "xml.namespace=${xml.namespace}")
        Log.e("MyResources", "xml.classAttribute=${xml.classAttribute}")
    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.WRAP_CONTENT
            it.window?.setLayout(width, height)
        }
    }
}