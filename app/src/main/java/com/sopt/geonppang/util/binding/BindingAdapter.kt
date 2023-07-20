package com.sopt.geonppang.util

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import java.text.DecimalFormat

@BindingAdapter("image")
fun ImageView.setImage(imageUrl: String) {
    this.load(imageUrl)
}

@BindingAdapter("selected")
fun View.setSelected(isSelected: Boolean) {
    this.isSelected = isSelected
}

@BindingAdapter("visibility")
fun View.setVisibility(isVisible: Boolean?) {
    if (isVisible == null) return
    this.visibility = if (isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("invisibility")
fun View.setInvisibility(isInvisible: Boolean?) {
    if (isInvisible == null) return
    this.visibility = if (isInvisible) View.VISIBLE else View.INVISIBLE
}

@BindingAdapter("priceAmount")
fun applyPriceFormat(view: TextView, price: Int) {
    val decimalFormat = DecimalFormat("#,###")
    view.text = decimalFormat.format(price) + "원"
}
