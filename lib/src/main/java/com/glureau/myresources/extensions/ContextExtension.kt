package com.glureau.myresources.extensions

import android.content.Context
import com.glureau.myresources.core.ResourceDefType

fun Context.getIdentifier(resName: String, type: ResourceDefType) =
    getIdentifier(resName, type, packageName)

private fun Context.getIdentifier(
    resName: String,
    type: ResourceDefType,
    packageName: String
): Int {
    val resId: Int = resources.getIdentifier(resName, type.typeName, packageName)
    if (resId == 0 && packageName.contains(".")) {
        return getIdentifier(resName, type, packageName.substringBeforeLast("."))
    }
    return resId
}
