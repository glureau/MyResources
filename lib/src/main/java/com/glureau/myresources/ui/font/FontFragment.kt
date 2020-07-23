package com.glureau.myresources.ui.font

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.glureau.myresources.R
import com.glureau.myresources.core.ResParser
import com.glureau.myresources.extensions.betterSmoothScrollToPosition
import com.glureau.myresources.extensions.setDivider
import com.glureau.myresources.ui.BaseFragment

class FontFragment : BaseFragment() {

    companion object : BaseFragmentCompanion() {
        override val FRAGMENT_TAG = "FontFragment"
    }

    private val adapter by lazy {
        FontAdapter(object : FontAdapter.FontAdapterListener {
            override fun onClick(fontRes: Int) {
                //FontDetailsDialogFragment.newInstance(fontRes)
                //    .show(childFragmentManager, FontDetailsDialogFragment.TAG)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.myr_fragment_font, container, false)
        val recyclerView: RecyclerView = root.findViewById(R.id.font_list)
        recyclerView.adapter = adapter
        recyclerView.setDivider(R.drawable.myr_recyclerview_divider)
        return root
    }

    override fun onResume() {
        super.onResume()
        adapter.submitList(ResParser.repository.getFonts())
        ResParser.repository.invalidateSignal = {
            adapter.submitList(ResParser.repository.getFonts())
            view?.findViewById<RecyclerView>(R.id.font_list)?.betterSmoothScrollToPosition(0)
        }
    }

    override fun onPause() {
        ResParser.repository.invalidateSignal = null
        super.onPause()
    }
}