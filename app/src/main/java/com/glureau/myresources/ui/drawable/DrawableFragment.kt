package com.glureau.myresources.ui.drawable

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.glureau.myresources.R
import com.glureau.myresources.core.ResourceAnalyser

class DrawableFragment : Fragment() {

    private val drawableAdapter by lazy { DrawableAdapter() }
    private val drawableViewModel: DrawableViewModel by lazy {
        ViewModelProviders.of(this).get(DrawableViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_drawable, container, false)
        val recyclerView: RecyclerView = root.findViewById(R.id.drawable_list)
        recyclerView.adapter = drawableAdapter
        return root
    }

    override fun onResume() {
        super.onResume()
        drawableAdapter.submitList(ResourceAnalyser.drawables.values.toList())
    }
}