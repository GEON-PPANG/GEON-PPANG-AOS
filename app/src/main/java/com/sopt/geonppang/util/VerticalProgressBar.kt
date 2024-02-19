package com.sopt.geonppang.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import com.sopt.geonppang.R

class VerticalProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var progress = 0
    private var max = 100
    private var backgroundDrawable: Drawable? = null
    private var progressDrawable: Drawable? = null

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.VerticalProgressBar)
        progress = typedArray.getInt(R.styleable.VerticalProgressBar_progress, 0)
        max = typedArray.getInt(R.styleable.VerticalProgressBar_max, 100)
        backgroundDrawable =
            typedArray.getDrawable(R.styleable.VerticalProgressBar_backgroundDrawable)
        progressDrawable =
            typedArray.getDrawable(R.styleable.VerticalProgressBar_progressDrawable)
        typedArray.recycle()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width.toFloat()
        val height = height.toFloat()
        val progressHeight = (height * progress / max)

        backgroundDrawable?.setBounds(0, 0, width.toInt(), height.toInt())
        progressDrawable?.setBounds(
            0,
            (height - progressHeight).toInt(),
            width.toInt(),
            height.toInt()
        )

        canvas.let { canvas ->
            backgroundDrawable?.draw(canvas)
            progressDrawable?.draw(canvas)
        }
    }

    fun setProgress(progress: Int) {
        this.progress = progress
        invalidate()
    }
}
