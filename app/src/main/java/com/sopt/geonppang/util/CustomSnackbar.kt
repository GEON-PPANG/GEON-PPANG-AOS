package com.sopt.geonppang.util

import android.content.res.Resources
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.sopt.geonppang.databinding.ViewSnackbarBinding

object CustomSnackbar {
    fun makeSnackbar(view: View, message: String) {
        val inflater = LayoutInflater.from(view.context)
        val binding = ViewSnackbarBinding.inflate(inflater, null, false)

        binding.tvViewSnackbar.text = message

        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
        val snackbarLayout = snackbar.view as ViewGroup

        val layoutParams = snackbarLayout.layoutParams as FrameLayout.LayoutParams
        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM
        layoutParams.bottomMargin = 112.toPx()
        snackbarLayout.layoutParams = layoutParams

        with(snackbarLayout) {
            removeAllViews()
            setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
            addView(binding.root)
        }

        snackbar.show()
    }

    private fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
}
