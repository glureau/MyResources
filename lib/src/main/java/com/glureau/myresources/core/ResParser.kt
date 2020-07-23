package com.glureau.myresources.core

import android.content.Context
import android.util.Log
import com.glureau.myresources.core.types.bool.BoolRes
import com.glureau.myresources.core.types.color.ColorRes
import com.glureau.myresources.core.types.dimen.DimenRes
import com.glureau.myresources.core.types.drawable.DrawableRes
import com.glureau.myresources.core.types.font.FontRes
import com.glureau.myresources.core.types.layout.LayoutRes
import com.glureau.myresources.core.types.string.StringRes
import dalvik.system.BaseDexClassLoader
import dalvik.system.DexFile
import java.lang.reflect.Field

object ResParser {

    val repository = ResRepository()

    fun init(appContext: Context) {
        Log.e("MyResources", "--------------------------------- START")

        getDexFiles(appContext)
            .flatMap { it.entries().asSequence() }
            .filter { it.contains(".R$") }
            .map { appContext.classLoader.loadClass(it) }
            .forEach { internalClass ->
                val resourceClassName =
                    internalClass.canonicalName?.substringBefore(".R$") ?: return@forEach
                Log.e("MyResources", "Available R class: ${internalClass.canonicalName}")

                repository.addPackageName(resourceClassName.substringBefore(".R."))

                when (internalClass.simpleName) {
                    ResourceDefType.Bool.typeName -> internalClass.fields.forEach {
                        repository.addBool(BoolRes(appContext, resourceClassName, it.name))
                    }
                    ResourceDefType.Color.typeName -> internalClass.fields.forEach {
                        repository.addColor(ColorRes(appContext, resourceClassName, it.name))
                    }
                    ResourceDefType.Dimen.typeName -> internalClass.fields.forEach {
                        repository.addDimen(DimenRes(appContext, resourceClassName, it.name))
                    }
                    ResourceDefType.Drawable.typeName -> internalClass.fields.forEach {
                        repository.addDrawable(DrawableRes(appContext, resourceClassName, it.name))
                    }
                    ResourceDefType.Font.typeName -> internalClass.fields.forEach {
                        repository.addFont(FontRes(appContext, resourceClassName, it.name))
                    }
                    ResourceDefType.Layout.typeName -> internalClass.fields.forEach {
                        repository.addLayout(LayoutRes(appContext, resourceClassName, it.name))
                    }
                    ResourceDefType.Strings.typeName -> internalClass.fields.forEach {
                        repository.addString(StringRes(appContext, resourceClassName, it.name))
                    }
                }
            }
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