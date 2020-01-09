package com.glureau.myresources.core

import com.glureau.myresources.core.filter.PackageFilter
import com.glureau.myresources.core.sorter.ColorSorter
import com.glureau.myresources.core.sorter.InverseSorter
import com.glureau.myresources.core.sorter.ResSorter
import com.glureau.myresources.core.types.bool.BoolRes
import com.glureau.myresources.core.types.color.ColorRes
import com.glureau.myresources.core.types.dimen.DimenRes
import com.glureau.myresources.core.types.drawable.DrawableRes

class ResAggregator {


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

    var packageFilter: PackageFilter = PackageFilter.KnownPackageFilter()
    var colorSorter: ResSorter<ColorRes> = InverseSorter(ColorSorter.HueColorSorter)

    fun addBool(res: BoolRes) = bools.add(res)
    fun getBools() = bools.filter(packageFilter::filter)

    fun addColor(res: ColorRes) = colors.add(res)
    fun getColors() = colors.filter(packageFilter::filter).sortedBy(colorSorter::sort)

    fun addDimen(res: DimenRes) = dimens.add(res)
    fun getDimens() = dimens.filter(packageFilter::filter)

    fun addDrawable(res: DrawableRes) = drawables.add(res)
    fun getDrawables() = drawables.filter(packageFilter::filter)

}