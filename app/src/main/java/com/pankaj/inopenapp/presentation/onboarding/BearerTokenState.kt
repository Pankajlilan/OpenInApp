package com.pankaj.inopenapp.presentation.onboarding

data class BearerTokenState(
  val isLoading: Boolean = false,
  val data: String? = null,
  val error: String = ""
)