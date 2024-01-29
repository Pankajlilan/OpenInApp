package com.pankaj.inopenapp.domain.repository

import com.pankaj.inopenapp.data.model.DashboardDTO
import com.pankaj.inopenapp.data.model.EmployeePostsItemDTO

interface DashboardRepository {
    suspend fun getDashboardData(): DashboardDTO
}