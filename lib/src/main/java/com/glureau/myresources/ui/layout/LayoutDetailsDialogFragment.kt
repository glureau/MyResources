package com.glureau.myresources.ui.layout

import android.content.res.XmlResourceParser
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.glureau.myresources.core.ResParser

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

    private fun logXml(xml: XmlResourceParser) {
        Log.e("MyResources", "xml.name=${xml.name}")
        Log.e("MyResources", "xml.namespace=${xml.namespace}")
        Log.e("MyResources", "xml.classAttribute=${xml.classAttribute}")
    }

}