package com.glureau.myresources.ui.drawable

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.glureau.myresources.R
import com.glureau.myresources.core.ResParser
import com.glureau.myresources.extensions.setDivider
import com.glureau.myresources.ui.BaseFragment
import com.glureau.myresources.ui.drawable.details.DrawableDetailsDialogFragment

class DrawableFragment : BaseFragment(), DrawableAdapter.AdapterListener {

    companion object : BaseFragmentCompanion() {
        override val FRAGMENT_TAG = "DrawableFragment"
    }

    private val drawableAdapter by lazy { DrawableAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.myr_fragment_drawable, container, false)
        val recyclerView: RecyclerView = root.findViewById(R.id.drawable_list)
        recyclerView.adapter = drawableAdapter
        recyclerView.setDivider(R.drawable.myr_recyclerview_divider)
        return root
    }

    override fun onResume() {
        super.onResume()
        drawableAdapter.submitList(ResParser.repository.getDrawables(requireContext()))
        ResParser.repository.invalidateSignal = {
            drawableAdapter.submitList(ResParser.repository.getDrawables(requireContext()))
            view?.findViewById<RecyclerView>(R.id.drawable_list)?.smoothScrollToPosition(0)
        }
    }

    override fun onPause() {
        ResParser.repository.invalidateSignal = null
        super.onPause()
    }

    override fun onClick(resName: String) {
        DrawableDetailsDialogFragment.newInstance(resName)
            .show(childFragmentManager, DrawableDetailsDialogFragment.TAG)
    }
}