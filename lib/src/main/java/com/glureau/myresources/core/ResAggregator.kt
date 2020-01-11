package com.glureau.myresources.core

import com.glureau.myresources.core.filter.PackageFilter
import com.glureau.myresources.core.sorter.InverseSorter
import com.glureau.myresources.core.sorter.ResSorter
import com.glureau.myresources.core.types.bool.BoolRes
import com.glureau.myresources.core.types.color.ColorRes
import com.glureau.myresources.core.types.color.ColorSorter
import com.glureau.myresources.core.types.dimen.DimenRes
import com.glureau.myresources.core.types.dimen.DimenSorter
import com.glureau.myresources.core.types.drawable.DrawableRes

class ResAggregator {

    var invalidateSignal: (() -> Unit)? = null

    private val packageNames = mutableSetOf<String>()
    val packages by lazy {
        packageNames
            .map { name ->
                val boolCount = bools.count { it.packageName == name }
                val colorCount = colors.count { it.packageName == name }
                val dimenCount = dimens.count { it.packageName == name }
                val drawableCount = drawables.count { it.packageName == name }
                Package(
                    name,
                    boolCount, colorCount, dimenCount, drawableCount,
                    totalCount = boolCount + colorCount + dimenCount + drawableCount
                )
            }
            .sortedBy { -it.totalCount }
            .filter { it.totalCount > 0 }
    }

    //private val anims = mutableListOf<Any>()
    //private val animators = mutableListOf<Any>()
    private val bools = mutableListOf<BoolRes>()
    private val colors = mutableListOf<ColorRes>()
    private val dimens = mutableListOf<DimenRes>()
    private val drawables = mutableListOf<DrawableRes>()
    //private val ids = mutableListOf<Any>()
    //private val interpolators = mutableListOf<Any>()
    //private val layouts = mutableListOf<Any>()
    //private val menus = mutableListOf<Any>()
    //private val mipmaps = mutableListOf<Any>()
    //private val navigations = mutableListOf<Any>()
    //private val strings = mutableListOf<Any>()
    //private val styles = mutableListOf<Any>()
    //private val styleables = mutableListOf<Any>()

    var packageFilter: PackageFilter = PackageFilter.KnownPackageFilter
        set(value) {
            field = value
            invalidateSignal?.invoke()
        }
    var dimenSorter: ResSorter<DimenRes> = DimenSorter
    var colorSorter: ResSorter<ColorRes> = InverseSorter(ColorSorter.HueColorSorter)

    fun addBool(res: BoolRes) = bools.add(res)
    fun getBools() = bools.filter(packageFilter::filter)

    fun addColor(res: ColorRes) = colors.add(res)
    fun getColors() = colors.filter(packageFilter::filter).sortedBy(colorSorter::sort)

    fun addDimen(res: DimenRes) = dimens.add(res)
    fun getDimens() = dimens.filter(packageFilter::filter).sortedBy(dimenSorter::sort)

    fun addDrawable(res: DrawableRes) = drawables.add(res)
    fun getDrawables() = drawables.filter(packageFilter::filter)

    fun addPackageName(packageName: String) {
        packageNames.add(packageName)
    }

}