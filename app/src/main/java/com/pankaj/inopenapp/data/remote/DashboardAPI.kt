package com.pankaj.inopenapp.data.remote

import com.pankaj.inopenapp.data.model.DashboardDTO
import retrofit2.http.GET

interface DashboardAPI {
  
  //  API to get Dashboard DataDTO
  @GET("dashboardNew")
  suspend fun getDashboardData(): DashboardDTO
}