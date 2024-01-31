package com.pankaj.inopenapp.domain.repository

import com.pankaj.inopenapp.data.model.DashboardDTO

interface DashboardRepository {
    suspend fun getDashboardData(): DashboardDTO
}