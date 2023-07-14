package com.sopt.geonppang.util.binding

import android.app.Activity
import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.load
import com.sopt.geonppang.R

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

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}
@BindingAdapter("highlightNumbers")
fun TextView.highlightNumbers(text: CharSequence?) {
    if (text.isNullOrEmpty()) {
        this.text = text
        return
    }

    val spannableString = SpannableString.valueOf(text)
    val numberPattern = "\\d+".toRegex()

    val color = ContextCompat.getColor(context, R.color.point_1)
    val span = ForegroundColorSpan(color)

    numberPattern.findAll(text).forEach { matchResult ->
        val startIndex = matchResult.range.start
        val endIndex = matchResult.range.endInclusive + 1
        spannableString.setSpan(span, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    this.text = spannableString
}
