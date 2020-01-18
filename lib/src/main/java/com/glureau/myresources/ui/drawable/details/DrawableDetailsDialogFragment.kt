package com.glureau.myresources.ui.drawable.details

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.glureau.myresources.R
import com.glureau.myresources.core.ResParser
import com.glureau.myresources.extensions.setDivider
import com.glureau.myresources.extensions.setupTransparentBackground
import com.glureau.myresources.extensions.startAndRepeatAnimations
import com.glureau.myresources.extensions.toHumanByteCount
import com.glureau.myresources.ui.common.FullscreenDrawablePreview


class DrawableDetailsDialogFragment : DialogFragment() {

    companion object {
        const val TAG = "DrawableDetailsDialogFragment"
        private const val ARG_NAME = "ARG_NAME"
        fun newInstance(resName: String) = DrawableDetailsDialogFragment().apply {
            arguments = bundleOf(
                ARG_NAME to resName
            )
        }
    }

    private val resName by lazy { requireArguments().getString(ARG_NAME) }

    val res by lazy {
        ResParser.repository.getDrawables(requireContext()).first { it.aggregatedBy == resName }
    }

    private val adapter =
        DrawableIDetailsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.myr_details_drawable, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.myr_details_drawable_title).text = resName
        val preview = view.findViewById<ImageView>(R.id.myr_details_drawable_preview)
        preview.setImageResource(res.resources.first().resId)
        preview.setupTransparentBackground()
        preview.startAndRepeatAnimations()

        val drawable = preview.drawable
        view.findViewById<TextView>(R.id.myr_details_drawable_details).text =
            "Type: " + drawable::class.java.simpleName + " (" + drawable.intrinsicWidth + "x" + drawable.intrinsicHeight + ")" +
                    if (drawable is BitmapDrawable) {
                        "\nSize: " + (if (drawable is BitmapDrawable) drawable.bitmap.allocationByteCount.toHumanByteCount() else "N/A (not a bitmap)")
                    } else {
                        ""
                    }


        view.setOnClickListener {
            FullscreenDrawablePreview.newInstance(
                res.resources.first().resId
            ).show(
                childFragmentManager,
                FullscreenDrawablePreview.TAG
            )
        }

        val recyclerView: RecyclerView = view.findViewById(R.id.myr_details_drawable_recycler)
        recyclerView.adapter = adapter
        recyclerView.setDivider(R.drawable.myr_recyclerview_divider)

        adapter.submitList(res.resources)
    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            val width = LayoutParams.MATCH_PARENT
            val height = LayoutParams.WRAP_CONTENT
            it.window?.setLayout(width, height)
        }
    }
}