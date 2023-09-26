package com.sopt.geonppang.util.extension

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showSnackbar(message: String, isShort: Boolean = true) {
    val duration = if (isShort) Snackbar.LENGTH_SHORT else Snackbar.LENGTH_LONG
    Snackbar.make(this, message, duration).show()
}

inline fun View.setOnSingleClickListener(
    delay: Long = 500L,
    crossinline block: (View) -> Unit
) {
    var isClickable = true
    setOnClickListener { view ->
        if (isClickable) {
            isClickable = false
            block(view)
            view.postDelayed({
                isClickable = true
            }, delay)
        }
    }
}
