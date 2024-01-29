package com.pankaj.inopenapp.domain.repository

interface EmployeeSearchRepository {
  suspend fun getBearerToken(): String
}