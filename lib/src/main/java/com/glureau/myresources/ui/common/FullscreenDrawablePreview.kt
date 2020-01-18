package com.glureau.myresources.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.glureau.myresources.R
import com.glureau.myresources.extensions.setupTransparentBackground
import com.glureau.myresources.extensions.startAndRepeatAnimations

class FullscreenDrawablePreview : DialogFragment() {

    companion object {
        val TAG = "FullscreenDrawablePreview"
        private val ARG = "ARG"
        fun newInstance(@DrawableRes drawableId: Int) = FullscreenDrawablePreview().apply {
            arguments = bundleOf(ARG to drawableId)
        }
    }

    private val drawableId by lazy { requireArguments().getInt(ARG) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.myr_fullscreen_drawable, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (view is ImageView) {
            view.setImageResource(drawableId)
            view.setupTransparentBackground()
            view.startAndRepeatAnimations()
        }
        view.setOnClickListener {
            dismiss()
        }
    }


    override fun onStart() {
        super.onStart()
        dialog?.let {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            it.window?.setLayout(width, height)
        }
    }
}