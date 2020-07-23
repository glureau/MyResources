package com.glureau.myresources.extensions

import android.content.Context
import com.glureau.myresources.core.ResourceDefType
import kotlin.math.pow

fun Context.getIdentifier(resName: String, type: ResourceDefType) =
    getIdentifier(resName, type, packageName)

private fun Context.getIdentifier(
    resName: String,
    type: ResourceDefType,
    packageName: String
): Int {
    var resId = 0
    allPossibleResourceNames(resName) { combination ->
        resId = resources.getIdentifier(combination, type.typeName, packageName)
        resId != 0
    }

    if (resId == 0) {
        return getIdentifier(resName, type, packageName.substringBeforeLast("."))
    }

    return resId
}

/**
 * Resources like strings can be defined with "." or "_"
 * Every "." is then transformed in "_" in the R.class to be Java compliant.
 * So we need to check all possible combinations of "." and "_" to ensure we match them all...
 */
fun allPossibleResourceNames(resName: String, action: (res: String) -> Boolean) {
    val indexes = resName.allIndexesOf("_")
    val possibilityCount = 2.0.pow(indexes.count()).toInt()
    for (i in 0 until possibilityCount) {
        var res = resName
        for (j in 0 until indexes.count()) {
            val shouldReplace = ((i shr j) % 2) == 1
            if (shouldReplace) {
                res = res.replaceRange(indexes[j], indexes[j] + 1, ".")
            }
        }
        if (action(res)) break
    }
}

private fun String.allIndexesOf(token: String): List<Int> {
    var index = -1
    val indexes = mutableListOf<Int>()
    while (true) {
        index = toString().indexOf(token, index + 1, ignoreCase = true)
        if (index >= 0) {
            indexes.add(index)
        } else {
            return indexes
        }
    }
}

fun main() {
    allPossibleResourceNames("a_b_c_d", { println(it); it == "a_b.c_d" && false })
    /**
     * Given "a_b_c"
     * Expecting (in no specific order):
     * a.b.c
     * a.b_c
     * a_b.c
     * a_b_c
     */
}