package com.pankaj.inopenapp.presentation.dashboard

import com.pankaj.inopenapp.domain.model.Dashboard

data class DashboardState(
  val isLoading: Boolean = false,
  val data: Dashboard? = null,
  val error: String = ""
)