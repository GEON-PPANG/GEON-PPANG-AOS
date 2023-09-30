package com.sopt.geonppang.util.extension

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import coil.Coil
import coil.request.ImageRequest

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}
fun Context.loadingImage(imageUrl: String, imageView: ImageView, loadingImage: Int) {
    imageUrl.let { url ->
        val imageRequest = ImageRequest.Builder(this)
            .data(url)
            .placeholder(loadingImage)
            .target(imageView)
            .build()
        Coil.imageLoader(this).enqueue(imageRequest)
    }
}
