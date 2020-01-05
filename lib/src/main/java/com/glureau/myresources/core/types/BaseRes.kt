package com.glureau.myresources.core.types

import android.content.Context
import com.glureau.myresources.core.ResourceDefType
import com.glureau.myresources.core.getIdentifier

open class BaseRes(
    val appContext: Context,
    val packageName: String,
    val resName: String,
    resourceDefType: ResourceDefType
) {
    val resId: Int = appContext.getIdentifier(resName, resourceDefType)
}