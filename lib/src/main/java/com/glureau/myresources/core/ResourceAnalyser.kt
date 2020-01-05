package com.glureau.myresources.core

import android.content.Context
import android.util.Log
import com.glureau.myresources.core.types.bool.BoolRes
import com.glureau.myresources.core.types.color.ColorRes
import com.glureau.myresources.core.types.dimen.DimenRes
import com.glureau.myresources.core.types.drawable.DrawableRes
import dalvik.system.BaseDexClassLoader
import dalvik.system.DexFile
import java.lang.reflect.Field

object ResourceAnalyser {

    //val anims = mutableListOf<Any>()
    //val animators = mutableListOf<Any>()
    val bools = mutableListOf<BoolRes>()
    val colors = mutableListOf<ColorRes>()
    val dimens = mutableListOf<DimenRes>()
    val drawables = mutableListOf<DrawableRes>()
    //val ids = mutableListOf<Any>()
    //val interpolators = mutableListOf<Any>()
    //val layouts = mutableListOf<Any>()
    //val menus = mutableListOf<Any>()
    //val mipmaps = mutableListOf<Any>()
    //val navigations = mutableListOf<Any>()
    //val strings = mutableListOf<Any>()
    //val styles = mutableListOf<Any>()
    //val styleables = mutableListOf<Any>()

    fun init(appContext: Context) {
        Log.e("MyResources", "--------------------------------- START")
        val packageName = appContext.packageName
        //TODO: Clean suffix auto-detect (or passed in BuildConfig?)
        val packageNameNoSuffix = packageName.replace(".debug", "")

        getDexFiles(appContext).flatMap { it.entries().asSequence() }
            .filter { it.startsWith(packageNameNoSuffix) && it.contains(".R$") }
            .map {
                appContext.classLoader.loadClass(it)
            }
            .forEach { internalClass ->
                when (internalClass.simpleName) {
                    ResourceDefType.Bool.typeName -> internalClass.fields.forEach {
                        bools += BoolRes(appContext, packageName, it.name)
                    }
                    ResourceDefType.Color.typeName -> internalClass.fields.forEach {
                        colors += ColorRes(appContext, packageName, it.name)
                    }
                    ResourceDefType.Dimen.typeName -> internalClass.fields.forEach {
                        dimens += DimenRes(appContext, packageName, it.name)
                    }
                    ResourceDefType.Drawable.typeName -> internalClass.fields.forEach {
                        drawables += DrawableRes(appContext, packageNameNoSuffix, it.name)
                    }
                }
            }
        colors.sortByDescending { it.hue }
        Log.e("MyResources", "--------------------------------- READY")
    }

    private fun getDexFiles(context: Context): Sequence<DexFile> {
        // Here we do some reflection to access the dex files from the class loader. These implementation details vary by platform version,
        // so we have to be a little careful, but not a huge deal since this is just for testing. It should work on 21+.
        // The source for reference is at:
        // https://android.googlesource.com/platform/libcore/+/oreo-release/dalvik/src/main/java/dalvik/system/BaseDexClassLoader.java
        val classLoader = context.classLoader as BaseDexClassLoader

        val pathListField = field("dalvik.system.BaseDexClassLoader", "pathList")
        val pathList = pathListField.get(classLoader) // Type is DexPathList

        val dexElementsField = field("dalvik.system.DexPathList", "dexElements")
        @Suppress("UNCHECKED_CAST")
        val dexElements =
            dexElementsField.get(pathList) as Array<Any> // Type is Array<DexPathList.Element>

        val dexFileField = field("dalvik.system.DexPathList\$Element", "dexFile")
        return dexElements.map {
            dexFileField.get(it) as DexFile
        }.asSequence()
    }

    private fun field(className: String, fieldName: String): Field {
        val clazz = Class.forName(className)
        val field = clazz.getDeclaredField(fieldName)
        field.isAccessible = true
        return field
    }
}