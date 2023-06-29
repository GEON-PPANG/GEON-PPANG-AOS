package com.sopt.geonppang.presentation.dummy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.geonppang.domain.repository.DummyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DummyViewModel @Inject constructor(
    private val dummyRepository: DummyRepository,
) : ViewModel() {

    fun uploadDummy() {
        viewModelScope.launch {
            dummyRepository.uploadDummy("name", "dummy")
                .onSuccess {
                }
                .onFailure {
                }
        }
    }
}
