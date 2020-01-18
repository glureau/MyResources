package com.glureau.myresources.extensions

import android.animation.Animator
import android.animation.ValueAnimator
import android.graphics.Shader
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.core.content.ContextCompat
import com.glureau.myresources.R
import com.glureau.myresources.ui.common.TileDrawable
import java.util.*


fun View.expand() {
    measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    val initialHeight = 0
    val targetHeight = measuredHeight

    // Older versions of Android (pre API 21) cancel animations for views with a height of 0.
    //v.getLayoutParams().height = 1;
    layoutParams.height = 0
    visibility = View.VISIBLE

    animateView(initialHeight, targetHeight)
}

fun View.collapse() {
    val initialHeight = measuredHeight
    val targetHeight = 0

    animateView(initialHeight, targetHeight)
}

private fun View.animateView(initialHeight: Int, targetHeight: Int) {
    val valueAnimator = ValueAnimator.ofInt(initialHeight, targetHeight)
    valueAnimator.addUpdateListener { animation ->
        layoutParams.height = animation.animatedValue as Int
        requestLayout()
    }
    valueAnimator.addListener(object : Animator.AnimatorListener {
        override fun onAnimationEnd(animation: Animator) {
            layoutParams.height = targetHeight
        }

        override fun onAnimationStart(animation: Animator) {}
        override fun onAnimationCancel(animation: Animator) {}
        override fun onAnimationRepeat(animation: Animator) {}
    })
    valueAnimator.duration = 300
    valueAnimator.interpolator = DecelerateInterpolator()
    valueAnimator.start()
}

fun View.setupTransparentBackground() {
    val drawable = ContextCompat.getDrawable(context, R.drawable.myr_transparent_background_tileable)!!
    background = TileDrawable(drawable, Shader.TileMode.REPEAT)
}

inline fun <reified T : View> View.filterViews(): List<T> {
    val filteredViews = mutableListOf<T>()
    var current: View? = this
    val stack = ArrayDeque<View>()
    while (current != null) {
        if (current is T) {
            filteredViews.add(current)
        }
        if (current is ViewGroup) {
            (0 until current.childCount).forEach {
                stack.add((current as ViewGroup).getChildAt(it))
            }
        }
        current = if (stack.isNotEmpty()) stack.pop() else null
    }
    return filteredViews
}