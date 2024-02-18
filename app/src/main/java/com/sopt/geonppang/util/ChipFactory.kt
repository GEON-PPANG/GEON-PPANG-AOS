package com.sopt.geonppang.util

import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import com.google.android.material.chip.Chip

object ChipFactory {
    fun create(layoutInflater: LayoutInflater, @LayoutRes layoutResId: Int): Chip =
        layoutInflater.inflate(layoutResId, null, false) as Chip
}
