package com.pankaj.inopenapp.presentation.dashboard

import com.pankaj.inopenapp.data.model.DashboardDTO
import com.pankaj.inopenapp.domain.model.EmployeePostsItem

data class DashboardState(
    val isLoading: Boolean = false,
    val data: DashboardDTO? = null,
    val error: String = ""
)