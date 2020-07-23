package com.glureau.myresources.ui.string

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.glureau.myresources.R
import com.glureau.myresources.core.ResParser
import com.glureau.myresources.extensions.setDivider
import com.glureau.myresources.ui.BaseFragment

class StringFragment : BaseFragment() {

    companion object : BaseFragmentCompanion() {
        override val FRAGMENT_TAG = "StringFragment"
    }

    private val stringAdapter by lazy { StringAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.myr_fragment_string, container, false)
        val recyclerView: RecyclerView = root.findViewById(R.id.string_list)
        recyclerView.adapter = stringAdapter
        recyclerView.setDivider(R.drawable.myr_recyclerview_divider)
        return root
    }

    override fun onResume() {
        super.onResume()
        stringAdapter.submitList(ResParser.repository.getStrings())
        ResParser.repository.invalidateSignal = {
            stringAdapter.submitList(ResParser.repository.getStrings())
            view?.findViewById<RecyclerView>(R.id.string_list)?.smoothScrollToPosition(0)
        }
    }

    override fun onPause() {
        ResParser.repository.invalidateSignal = null
        super.onPause()
    }

}