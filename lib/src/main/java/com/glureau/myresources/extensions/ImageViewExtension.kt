package com.glureau.myresources.extensions

import android.graphics.Shader
import android.graphics.drawable.Animatable2
import android.graphics.drawable.AnimatedStateListDrawable
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.glureau.myresources.R
import com.glureau.myresources.ui.common.TileDrawable


fun ImageView.startAndRepeatAnimations() {
    val drawable: Drawable? = drawable

    when {
        drawable is AnimatedVectorDrawable && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
            val animCallback = object : Animatable2.AnimationCallback() {
                override fun onAnimationEnd(d: Drawable?) {
                    this@startAndRepeatAnimations.postDelayed({
                        // Check that we still are on the
                        if (d is AnimatedVectorDrawable) {
                            d.start()
                        }
                    }, 600)
                }
            }

            addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
                override fun onViewDetachedFromWindow(v: View?) {
                    drawable.unregisterAnimationCallback(animCallback)
                }

                override fun onViewAttachedToWindow(v: View?) {
                    // No need to re-attach
                }
            })

            drawable.registerAnimationCallback(animCallback)
            drawable.start()
        }
        drawable is AnimatedStateListDrawable -> {
            // TODO: List all possible transitions, and alternate states to showcase the capabilities of the drawable
        }
    }
}

fun ImageView.setupTransparentBackground() {
    val drawable = ContextCompat.getDrawable(context, R.drawable.myr_transparent_background_tileable)!!
    background = TileDrawable(drawable, Shader.TileMode.REPEAT)
}