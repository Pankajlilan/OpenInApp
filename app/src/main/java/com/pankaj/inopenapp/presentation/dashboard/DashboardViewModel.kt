package com.pankaj.inopenapp.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pankaj.inopenapp.common.Resource
import com.pankaj.inopenapp.domain.use_case.GetDashboardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val dashboardUseCase: GetDashboardUseCase) :
    ViewModel() {

    private val _dashboardData = MutableStateFlow(DashboardState())
    val dashboardData: StateFlow<DashboardState> = _dashboardData

      fun getDashboardData() {
        dashboardUseCase().onEach {
            when (it) {
                is Resource.Loading -> {
                    _dashboardData.value = DashboardState(isLoading = true)
                }
                is Resource.Success -> {
                    _dashboardData.value = DashboardState(data = it.data)
                }
                is Resource.Error -> {
                    _dashboardData.value = DashboardState(error = it.message ?: "")
                }
            }
        }.launchIn(viewModelScope)
    }
}