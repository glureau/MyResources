package com.glureau.myresources.core.types

import android.content.Context
import com.glureau.myresources.core.ResourceDefType
import com.glureau.myresources.extensions.getIdentifier

open class BaseRes(
    val appContext: Context,
    val resourceClassName: String,
    val resName: String,
    resourceDefType: ResourceDefType
) {
    val resId: Int = appContext.getIdentifier(resName, resourceDefType)
    val packageName = resourceClassName.substringBefore(".R.")
    open val definitionForQuery: String = "$resName $resId $packageName"
}