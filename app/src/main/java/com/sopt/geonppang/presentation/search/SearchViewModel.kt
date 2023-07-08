package com.sopt.geonppang.presentation.search

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class SearchViewModel : ViewModel() {
    val inputSearch = MutableStateFlow("")
}