package com.pankaj.inopenapp.data.repository

import com.pankaj.inopenapp.domain.repository.EmployeeSearchRepository
import java.io.File
import java.io.InputStream

class EmployeeSearchRepositoryImpl :
  EmployeeSearchRepository {
  
  override suspend fun getBearerToken(): String {
      val inputStream: InputStream = File("example.txt").inputStream()
      val inputString = inputStream.bufferedReader().use { it.readText() }
      println(inputString)
    return ""
  }
}