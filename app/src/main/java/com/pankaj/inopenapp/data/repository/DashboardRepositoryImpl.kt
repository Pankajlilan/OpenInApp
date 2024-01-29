package com.pankaj.inopenapp.data.repository

import com.pankaj.inopenapp.data.model.DashboardDTO
import com.pankaj.inopenapp.data.model.EmployeePostsItemDTO
import com.pankaj.inopenapp.data.remote.DashboardAPI
import com.pankaj.inopenapp.domain.repository.DashboardRepository

class DashboardRepositoryImpl(private val dashboardAPI: DashboardAPI) : DashboardRepository {

//    Implement the Employee Posts Interface to call the Employee API
    override suspend fun getDashboardData(): DashboardDTO {
        return dashboardAPI.getDashboardData()
    }
}