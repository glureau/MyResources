package com.glureau.myresources.ui.color

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.glureau.myresources.R
import com.glureau.myresources.core.ResourceAnalyser
import com.glureau.myresources.ui.BaseFragment

class ColorFragment : BaseFragment() {

    companion object : BaseFragmentCompanion() {
        override val FRAGMENT_TAG = "ColorFragment"
    }

    private val colorAdapter by lazy { ColorAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.myr_fragment_color, container, false)
        val recyclerView: RecyclerView = root.findViewById(R.id.color_list)
        recyclerView.adapter = colorAdapter
        return root
    }

    override fun onResume() {
        super.onResume()
        colorAdapter.submitList(ResourceAnalyser.aggregator.getColors())
        ResourceAnalyser.aggregator.invalidateSignal = {
            colorAdapter.submitList(ResourceAnalyser.aggregator.getColors())
            view?.findViewById<RecyclerView>(R.id.color_list)?.smoothScrollToPosition(0)
        }
    }

    override fun onPause() {
        ResourceAnalyser.aggregator.invalidateSignal = null
        super.onPause()
    }
}