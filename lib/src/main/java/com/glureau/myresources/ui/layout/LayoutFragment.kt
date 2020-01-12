package com.glureau.myresources.ui.layout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.glureau.myresources.R
import com.glureau.myresources.core.ResParser
import com.glureau.myresources.extensions.setDivider
import com.glureau.myresources.ui.BaseFragment

class LayoutFragment : BaseFragment() {

    companion object : BaseFragmentCompanion() {
        override val FRAGMENT_TAG = "LayoutFragment"
    }

    private val adapter by lazy {
        LayoutAdapter(object : LayoutAdapter.LayoutAdapterListener {
            override fun onClick(layoutRes: Int) {
                LayoutDetailsDialogFragment.newInstance(layoutRes)
                    .show(childFragmentManager, LayoutDetailsDialogFragment.TAG)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.myr_fragment_layout, container, false)
        val recyclerView: RecyclerView = root.findViewById(R.id.layout_list)
        recyclerView.adapter = adapter
        recyclerView.setDivider(R.drawable.myr_recyclerview_divider)
        return root
    }

    override fun onResume() {
        super.onResume()
        adapter.submitList(ResParser.repository.getLayouts())
        ResParser.repository.invalidateSignal = {
            adapter.submitList(ResParser.repository.getLayouts())
            view?.findViewById<RecyclerView>(R.id.layout_list)?.smoothScrollToPosition(0)
        }
    }

    override fun onPause() {
        ResParser.repository.invalidateSignal = null
        super.onPause()
    }
}