package com.sopt.geonppang.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load

@BindingAdapter("image")
fun ImageView.setImage(imageUrl: String) {
    this.load(imageUrl)
}

@BindingAdapter("selected")
fun View.setSelected(isSelected: Boolean?) {
    if (isSelected == null) return
    this.isSelected = isSelected
}

@BindingAdapter("visibility")
fun View.setVisibility(isVisible: Boolean?) {
    if (isVisible == null) return
    this.visibility = if (isVisible) View.VISIBLE else View.GONE
}
