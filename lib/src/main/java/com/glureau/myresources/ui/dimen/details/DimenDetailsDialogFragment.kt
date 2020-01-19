package com.glureau.myresources.ui.dimen.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.glureau.myresources.R
import com.glureau.myresources.core.ResParser
import com.glureau.myresources.core.types.dimen.DimenRes
import com.glureau.myresources.extensions.setDivider
import kotlin.math.abs


class DimenDetailsDialogFragment : DialogFragment() {

    companion object {
        const val TAG = "DimenDetailsDialogFragment"
        private const val ARG = "ARG"
        fun newInstance(valueStr: String) = DimenDetailsDialogFragment().apply {
            arguments = bundleOf(
                ARG to valueStr
            )
        }
    }

    private val valueStr by lazy { requireArguments().getString(ARG) }

    private val agg by lazy {
        ResParser.repository.getDimens(requireContext()).first { it.aggregatedBy == valueStr }
    }
    private val res by lazy { agg.resources.first() }

    private val adapter = DimenDetailsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.myr_details_dimen, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.myr_details_dimen_name).text = valueStr
        view.findViewById<TextView>(R.id.myr_details_dimen_details).text = """
            Unit: ${res.unit.definition}
            getDimension() = ${res.valueFloat}
            getDimensionPixelSize() = ${res.valuePixelSize}
            getDimensionPixelOffset() = ${res.valuePixelOffset}
            getString() = ${res.valueString}
            
            Device settings:
            fontScale = ${resources.configuration.fontScale}
            scaledDensity = ${resources.displayMetrics.scaledDensity}
        """.trimIndent()

        val preview = view.findViewById<View>(R.id.myr_details_dimen_preview)
        preview.isVisible = res.unit != DimenRes.UNIT.SP
        preview.layoutParams = preview.layoutParams.apply {
            val value = when (res.unit) {
                DimenRes.UNIT.PX, DimenRes.UNIT.DP, DimenRes.UNIT.NONE -> abs(res.valuePixelSize ?: 0)
                DimenRes.UNIT.PERCENT -> ((res.rawValue ?: 0f) / 100f * view.width).toInt()
                else -> 0
            }
            width = value
            height = value
        }

        val previewText = view.findViewById<TextView>(R.id.myr_details_dimen_text_size)
        previewText.isVisible = res.unit == DimenRes.UNIT.SP
        previewText.textSize = res.rawValue ?: 0f

        val recyclerView: RecyclerView = view.findViewById(R.id.myr_details_dimen_recycler)
        recyclerView.adapter = adapter
        recyclerView.setDivider(R.drawable.myr_recyclerview_divider)

        adapter.submitList(agg.resources)
    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            val width = LayoutParams.MATCH_PARENT
            val height = LayoutParams.WRAP_CONTENT
            it.window?.setLayout(width, height)
        }
    }
}