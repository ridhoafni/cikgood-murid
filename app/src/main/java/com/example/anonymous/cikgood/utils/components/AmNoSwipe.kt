package com.example.anonymous.cikgood.utils.components

import android.support.design.widget.BaseTransientBottomBar
import android.view.View

/**
 * Created by Al
 * on 08/04/19 | 11:59
 */
internal class AmNoSwipe : BaseTransientBottomBar.Behavior() {
    override fun canSwipeDismissView(child: View):Boolean {
        return false
    }
}