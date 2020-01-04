package com.glureau.myresources.core

import android.content.Context
import android.util.Log
import com.glureau.myresources.core.types.bool.BoolRes
import com.glureau.myresources.core.types.drawable.DrawableRes
import dalvik.system.BaseDexClassLoader
import dalvik.system.DexFile
import java.lang.reflect.Field

object ResourceAnalyser {

    //val anims = mutableMapOf<String, Any>()
    //val animators = mutableMapOf<String, Any>()
    val bools = mutableMapOf<String, BoolRes>()
    val colors = mutableMapOf<String, Any>()
    val dimens = mutableMapOf<String, Any>()
    val drawables = mutableMapOf<String, DrawableRes>()
    val ids = mutableMapOf<String, Any>()
    val interpolators = mutableMapOf<String, Any>()
    val layouts = mutableMapOf<String, Any>()
    val menus = mutableMapOf<String, Any>()
    val mipmaps = mutableMapOf<String, Any>()
    val navigations = mutableMapOf<String, Any>()
    val strings = mutableMapOf<String, Any>()
    val styles = mutableMapOf<String, Any>()
    val styleables = mutableMapOf<String, Any>()

    fun init(appContext: Context, packageName: String) {
        Log.e("MyResources", "---------------------------------")
        getDexFiles(appContext).flatMap { it.entries().asSequence() }
            .filter { it.startsWith(packageName) && it.contains(".R$") }
            .map { appContext.classLoader.loadClass(it) }
            .forEach { internalClass ->
                when (internalClass.simpleName) {
                    "drawable" -> internalClass.fields.forEach {
                        drawables[it.name] = DrawableRes(appContext, packageName, it.name)
                    }
                    "bool" -> internalClass.fields.forEach {
                        bools[it.name] = BoolRes(appContext, packageName, it.name)
                    }
                }
            }

        Log.e("MyResources", "---------------------------------")
        Log.e(
            "MyResources",
            "drawables=${drawables.toList().joinToString("\n") { it.second.print(appContext) }}"
        )
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