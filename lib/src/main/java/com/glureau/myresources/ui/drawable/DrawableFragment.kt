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

class DrawableFragment : BaseFragment() {

    companion object : BaseFragmentCompanion() {
        override val FRAGMENT_TAG = "DrawableFragment"
    }

    private val drawableAdapter by lazy { DrawableAdapter() }

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
        drawableAdapter.submitList(ResParser.repository.getDrawables())
        ResParser.repository.invalidateSignal = {
            drawableAdapter.submitList(ResParser.repository.getDrawables())
            view?.findViewById<RecyclerView>(R.id.drawable_list)?.smoothScrollToPosition(0)
        }
    }

    override fun onPause() {
        ResParser.repository.invalidateSignal = null
        super.onPause()
    }
}