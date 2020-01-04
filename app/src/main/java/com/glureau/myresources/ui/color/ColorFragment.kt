package com.glureau.myresources.ui.color

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.glureau.myresources.R
import com.glureau.myresources.core.ResourceAnalyser

class ColorFragment : Fragment() {


    private val colorAdapter by lazy { ColorAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_color, container, false)
        val recyclerView: RecyclerView = root.findViewById(R.id.color_list)
        recyclerView.adapter = colorAdapter
        return root
    }

    override fun onResume() {
        super.onResume()
        colorAdapter.submitList(ResourceAnalyser.colors)
    }
}