package com.glureau.myresources.ui.bool

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.glureau.myresources.R
import com.glureau.myresources.core.ResourceAnalyser

class BoolFragment : Fragment() {


    private val boolAdapter by lazy { BoolAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.myr_fragment_bool, container, false)
        val recyclerView: RecyclerView = root.findViewById(R.id.bool_list)
        recyclerView.adapter = boolAdapter
        return root
    }

    override fun onResume() {
        super.onResume()
        boolAdapter.submitList(ResourceAnalyser.bools)
    }
}