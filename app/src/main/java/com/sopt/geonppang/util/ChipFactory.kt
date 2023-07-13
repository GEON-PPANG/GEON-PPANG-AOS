package com.sopt.geonppang.util

import android.view.LayoutInflater
import com.google.android.material.chip.Chip
import com.sopt.geonppang.R

object ChipFactory {
    fun create(layoutInflater: LayoutInflater): Chip =
        layoutInflater.inflate(R.layout.view_chip_review, null, false) as Chip
}
