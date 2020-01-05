package com.glureau.myresources.core

import android.content.Context

enum class ResourceDefType(val typeName: String) {
    Bool("bool"),
    Color("color"),
    Drawable("drawable")
}

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
