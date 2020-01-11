package com.glureau.myresources.core.filter

import com.glureau.myresources.core.types.BaseRes

sealed class PackageFilter : ResFilter<BaseRes> {

    object KnownPackageFilter : PackageFilter() {

        private val KNOWN_PACKAGES = listOf(
            "androidx.",
            "com.google.maps.android.R",
            "com.bumptech.glide.R",
            "com.google.android.material.R",
            "com.google.firebase.R",
            "com.google.firebase.icing.R",
            "com.google.firebase.crash.R",
            "dagger.android.support.R",
            "me.mvdw.recyclerviewmergeadapter.R",
            "com.glureau.myresources.R"
        )

        override fun filter(res: BaseRes): Boolean {
            KNOWN_PACKAGES.forEach {
                if (res.resourceClassName.startsWith(it)) return false
            }
            return true
        }
    }

    class SpecificPackageFilter(private val packageName: String) : PackageFilter() {
        override fun filter(res: BaseRes) = res.packageName == packageName
    }
}