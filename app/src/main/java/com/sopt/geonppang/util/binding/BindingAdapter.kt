package com.sopt.geonppang.util

import android.view.View
import android.widget.ImageView
import androidx.core.view.isGone
import androidx.databinding.BindingAdapter
import coil.load

@BindingAdapter("image")
fun ImageView.setImage(imageUrl: String) {
    this.load(imageUrl)
}

@BindingAdapter("visibility")
fun View.setVisibility(isVisible: Boolean?) {
    if (isVisible == null) return
    this.isGone = isVisible
}
