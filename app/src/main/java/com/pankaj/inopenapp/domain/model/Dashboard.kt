package com.pankaj.inopenapp.domain.model

data class Dashboard(
    val appliedCampaign: Int,
    val data: Data,
    val extraIncome: Double,
    val linksCreatedToday: Int,
    val message: String,
    val startTime: String,
    val status: Boolean,
    val statusCode: Int,
    val supportWhatsappNumber: String,
    val todayClicks: Int,
    val topLocation: String,
    val topSource: String,
    val totalClicks: Int,
    val totalLinks: Int
)