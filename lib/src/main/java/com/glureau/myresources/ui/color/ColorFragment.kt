package com.glureau.myresources.ui.color

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.glureau.myresources.R
import com.glureau.myresources.core.ResParser
import com.glureau.myresources.extensions.setDivider
import com.glureau.myresources.ui.BaseFragment

class ColorFragment : BaseFragment() {

    companion object : BaseFragmentCompanion() {
        override val FRAGMENT_TAG = "ColorFragment"
    }

    private val colorAdapter by lazy { AggregatedColorAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.myr_fragment_color, container, false)
        val recyclerView: RecyclerView = root.findViewById(R.id.color_list)
        recyclerView.adapter = colorAdapter
        recyclerView.setDivider(R.drawable.myr_recyclerview_divider)
        return root
    }

    override fun onResume() {
        super.onResume()
        colorAdapter.submitList(ResParser.repository.getColors(requireContext()))
        ResParser.repository.invalidateSignal = {
            colorAdapter.submitList(ResParser.repository.getColors(requireContext()))
            view?.findViewById<RecyclerView>(R.id.color_list)?.smoothScrollToPosition(0)
        }
    }

    override fun onPause() {
        ResParser.repository.invalidateSignal = null
        super.onPause()
    }
}