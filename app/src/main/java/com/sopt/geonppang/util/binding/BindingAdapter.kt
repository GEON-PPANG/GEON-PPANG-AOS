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
fun View.setSelected(isSelected: Boolean) {
    this.isSelected = isSelected
}
