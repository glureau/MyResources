package com.glureau.myresources.extensions

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator


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