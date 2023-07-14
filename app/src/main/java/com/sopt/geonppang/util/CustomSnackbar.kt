package com.sopt.geonppang.util

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.SnackbarLayout
import com.sopt.geonppang.databinding.ViewSnackbarBinding

object CustomSnackbar {
    fun makeSnackbar(view: View, message: String, bottomMargin: Int) {
        val inflater = LayoutInflater.from(view.context)
        val binding = ViewSnackbarBinding.inflate(inflater, null, false)

        binding.tvViewSnackbar.text = message

        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
        val snackbarLayout = snackbar.view as SnackbarLayout

        with(snackbarLayout) {
            removeAllViews()
            setPadding(0, 0, 0, bottomMargin.toPx())
            setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
            addView(binding.root)
        }

        snackbar.show()
    }

    private fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
}
