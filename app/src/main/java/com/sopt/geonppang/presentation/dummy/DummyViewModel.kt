package com.sopt.geonppang.presentation.dummy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.geonppang.data.repository.DummyRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DummyViewModel @Inject constructor(
    private val dummyRepositoryImpl: DummyRepositoryImpl,
) : ViewModel() {

    fun uploadDummy() {
        viewModelScope.launch {
            dummyRepositoryImpl.uploadDummy("name", "dummy")
                .onSuccess {
                }
                .onFailure {
                }
        }
    }
}
