package com.glureau.myresources.ui

import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {
    abstract class BaseFragmentCompanion {
        abstract val FRAGMENT_TAG: String
    }
}