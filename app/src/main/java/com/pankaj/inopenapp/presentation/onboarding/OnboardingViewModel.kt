package com.pankaj.inopenapp.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pankaj.inopenapp.common.Resource
import com.pankaj.inopenapp.domain.use_case.SearchEmployeeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val searchEmployeeUseCase: SearchEmployeeUseCase
) : ViewModel() {


    private val _bearerToken = MutableStateFlow(BearerTokenState())
    val bearerToken: StateFlow<BearerTokenState> = _bearerToken

    //      Calling the Employee Search Use Case and Handling the response with multiple status
    //      Such as isLoading, error, and handling the dataDTO
    fun getBearerToken() {
        searchEmployeeUseCase().onEach {
            when (it) {
                is Resource.Loading -> {
                    _bearerToken.value = BearerTokenState(isLoading = true)
                }
                is Resource.Success -> {
                    _bearerToken.value = BearerTokenState(data = it.data)
                }
                is Resource.Error -> {
                    _bearerToken.value = BearerTokenState(error = it.message ?: "")
                }
            }
        }.launchIn(viewModelScope)
    }
}