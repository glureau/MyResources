package com.glureau.myresources.ui.dimen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.glureau.myresources.R
import com.glureau.myresources.core.ResourceAnalyser
import com.glureau.myresources.ui.BaseFragment

class DimenFragment : BaseFragment() {

    companion object : BaseFragmentCompanion() {
        override val FRAGMENT_TAG = "DimenFragment"
    }

    private val dimenAdapter by lazy { DimenAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.myr_fragment_bool, container, false)
        val recyclerView: RecyclerView = root.findViewById(R.id.bool_list)
        recyclerView.adapter = dimenAdapter
        return root
    }

    override fun onResume() {
        super.onResume()
        dimenAdapter.submitList(ResourceAnalyser.aggregator.getDimens())
        ResourceAnalyser.aggregator.invalidateSignal = {
            dimenAdapter.submitList(ResourceAnalyser.aggregator.getDimens())
            view?.findViewById<RecyclerView>(R.id.bool_list)?.smoothScrollToPosition(0)
        }
    }

    override fun onPause() {
        ResourceAnalyser.aggregator.invalidateSignal = null
        super.onPause()
    }
}