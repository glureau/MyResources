package com.glureau.myresources.core

import android.content.Context
import com.glureau.myresources.core.aggregator.AggregatedByNameRes
import com.glureau.myresources.core.aggregator.BaseResNameAggregator
import com.glureau.myresources.core.aggregator.ResAggregator
import com.glureau.myresources.core.filter.PackageFilter
import com.glureau.myresources.core.filter.SearchFilter
import com.glureau.myresources.core.sorter.InverseSorter
import com.glureau.myresources.core.sorter.ResSorter
import com.glureau.myresources.core.types.bool.BoolRes
import com.glureau.myresources.core.types.bool.FontRes
import com.glureau.myresources.core.types.color.AggregatedColorRes
import com.glureau.myresources.core.types.color.ColorAggregator
import com.glureau.myresources.core.types.color.ColorRes
import com.glureau.myresources.core.types.color.ColorSorter
import com.glureau.myresources.core.types.dimen.AggregatedDimenRes
import com.glureau.myresources.core.types.dimen.DimenAggregator
import com.glureau.myresources.core.types.dimen.DimenRes
import com.glureau.myresources.core.types.dimen.DimenSorter
import com.glureau.myresources.core.types.drawable.DrawableRes
import com.glureau.myresources.core.types.layout.LayoutRes

class ResRepository {

    var invalidateSignal: (() -> Unit)? = null

    private val packageNames = mutableSetOf<String>()
    val packages by lazy {
        packageNames
            .map { name ->
                val boolCount = bools.count { it.packageName == name }
                val colorCount = colors.count { it.packageName == name }
                val dimenCount = dimens.count { it.packageName == name }
                val drawableCount = drawables.count { it.packageName == name }
                val fontCount = fonts.count { it.packageName == name }
                val layoutCount = layouts.count { it.packageName == name }
                Package(
                    name,
                    boolCount, colorCount, dimenCount, drawableCount, fontCount, layoutCount,
                    totalCount = boolCount + colorCount + dimenCount + drawableCount + fontCount + layoutCount
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
    private val fonts = mutableListOf<FontRes>()
    //private val ids = mutableListOf<Any>()
    //private val interpolators = mutableListOf<Any>()
    private val layouts = mutableListOf<LayoutRes>()
    //private val menus = mutableListOf<Any>()
    //private val mipmaps = mutableListOf<Any>()
    //private val navigations = mutableListOf<Any>()
    //private val strings = mutableListOf<Any>()
    //private val styles = mutableListOf<Any>()
    //private val styleables = mutableListOf<Any>()

    var packageFilter: PackageFilter = PackageFilter.AllPackageFilter
        set(value) {
            field = value
            invalidateSignal?.invoke()
        }
    private val searchFilter = SearchFilter()
    var dimenSorter: ResSorter<DimenRes> = DimenSorter
    var dimenAggregator: ResAggregator<DimenRes, AggregatedDimenRes> = DimenAggregator
    var colorSorter: ResSorter<ColorRes> = InverseSorter(ColorSorter.HueColorSorter)
    val colorAggregator: ResAggregator<ColorRes, AggregatedColorRes> = ColorAggregator
    val drawableAggregator: ResAggregator<DrawableRes, AggregatedByNameRes<DrawableRes>> =
        BaseResNameAggregator()

    fun addBool(res: BoolRes) = bools.add(res)
    fun getBools() = bools
        .filter(searchFilter::filter)
        .filter(packageFilter::filter)

    fun addColor(res: ColorRes) = colors.add(res)
    fun getColors(context: Context) = colors
        .filter(searchFilter::filter)
        .filter(packageFilter::filter)
        .sortedBy(colorSorter::sort)
        .let { colorAggregator.aggregate(context, it) }

    fun addDimen(res: DimenRes) = dimens.add(res)
    fun getDimens(context: Context) = dimens
        .filter(searchFilter::filter)
        .filter(packageFilter::filter)
        .sortedBy(dimenSorter::sort)
        .let { dimenAggregator.aggregate(context, it) }

    fun addDrawable(res: DrawableRes) = drawables.add(res)
    fun getDrawables(context: Context) = drawables
        .filter(searchFilter::filter)
        .filter(packageFilter::filter)
        .let { drawableAggregator.aggregate(context, it) }

    fun addFont(res: FontRes) = fonts.add(res)
    fun getFonts(context: Context) = fonts
        .filter(searchFilter::filter)
        .filter(packageFilter::filter)

    fun addLayout(res: LayoutRes) = layouts.add(res)
    fun getLayouts() = layouts
        .filter(searchFilter::filter)
        .filter(packageFilter::filter)

    fun addPackageName(packageName: String) {
        packageNames.add(packageName)
    }

    fun searchQuery(query: String?) {
        searchFilter.query = query
        invalidateSignal?.invoke()
    }

}