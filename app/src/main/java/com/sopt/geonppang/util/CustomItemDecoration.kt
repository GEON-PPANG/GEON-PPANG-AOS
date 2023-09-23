package com.sopt.geonppang.util

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView
import com.sopt.geonppang.R
import kotlin.math.roundToInt

class CustomItemDecoration(
    private val context: Context,
    private val isFirstDividerDrawing: Boolean = true
) : RecyclerView.ItemDecoration() {
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val widthMargin = 10.dp
        val height = 1.dp

        val left = parent.paddingLeft.toFloat()
        val right = parent.width - parent.paddingRight.toFloat()
        val paint = Paint().apply { color = context.getColor(R.color.gray_200) }
        for (i in 0 until parent.childCount) {
            val view = parent.getChildAt(i)
            val top =
                view.bottom.toFloat() + (view.layoutParams as RecyclerView.LayoutParams).bottomMargin
            val bottom = top + height

            when (isFirstDividerDrawing) {
                true -> {
                    if (i != parent.childCount - 1) {
                        c.drawRect(left + widthMargin, top, right - widthMargin, bottom, paint)
                    }
                }

                false -> {
                    if (i != 0 && i != parent.childCount - 1)
                        c.drawRect(left + widthMargin, top, right - widthMargin, bottom, paint)
                }
            }
        }
    }
}

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).roundToInt()
