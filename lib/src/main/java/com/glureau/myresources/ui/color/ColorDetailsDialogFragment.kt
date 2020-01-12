package com.glureau.myresources.ui.color

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.glureau.myresources.R
import com.glureau.myresources.core.ResParser
import com.glureau.myresources.extensions.setDivider
import com.glureau.myresources.extensions.toHex

class ColorDetailsDialogFragment : DialogFragment() {

    companion object {
        const val TAG = "ColorDetailsDialogFragment"
        private const val ARG_COLOR = "ARG_COLOR"
        fun newInstance(color: Int) = ColorDetailsDialogFragment().apply {
            arguments = bundleOf(
                ARG_COLOR to color
            )
        }
    }

    private val color by lazy { requireArguments().getInt(ARG_COLOR) }

    private val detailsColorAdapter = ColorDetailsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.myr_details_color, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.myr_details_color_title).text = "#" + color.toHex()

        val recyclerView: RecyclerView = view.findViewById(R.id.myr_details_color_recycler)
        recyclerView.adapter = detailsColorAdapter
        recyclerView.setDivider(R.drawable.myr_recyclerview_divider)

        detailsColorAdapter.submitList(ResParser.repository.getColors(requireContext()).first { it.color == color }.resources)
    }

}