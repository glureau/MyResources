package com.glureau.myresources.core.types

import android.content.Context
import android.util.Log
import com.glureau.myresources.core.ResourceDefType
import com.glureau.myresources.extensions.getIdentifier

open class BaseRes(
    val appContext: Context,
    val resourceClassName: String,
    val resName: String,
    private val resourceDefType: ResourceDefType
) {
    val resId: Int = appContext.getIdentifier(resName, resourceDefType)
    fun resId(context: Context): Int = context.getIdentifier(resName, resourceDefType).also {
        if (resId != 0) {
            Log.e("BaseRes", "Cannot find a proper identifier for $resName")
        }
    }
    val packageName = resourceClassName.substringBefore(".R.")
    open val definitionForQuery: String = "$resName $resId $packageName"

    init {
        if (resId == 0) {
            Log.e("BaseRes", "Cannot find a proper identifier for $resName")
        }
    }
}