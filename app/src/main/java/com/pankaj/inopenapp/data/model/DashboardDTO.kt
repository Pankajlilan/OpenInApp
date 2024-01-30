package com.pankaj.inopenapp.data.model

import com.pankaj.inopenapp.domain.model.Dashboard

data class DashboardDTO(
    val applied_campaign: Int?,
    val data: DataDTO,
    val extra_income: Double?,
    val links_created_today: Int?,
    val message: String?,
    val startTime: String?,
    val status: Boolean?,
    val statusCode: Int?,
    val support_whatsapp_number: String?,
    val today_clicks: Int?,
    val top_location: String?,
    val top_source: String?,
    val total_clicks: Int?,
    val total_links: Int?
)

fun DashboardDTO.toDashboard(): Dashboard {
    return Dashboard(
        appliedCampaign = this.applied_campaign ?: 0,
        data = this.data.toData(),
        extraIncome = this.extra_income ?: 0.0,
        linksCreatedToday = this.links_created_today ?: 0,
        message = this.message ?: "",
        startTime = this.startTime ?: "",
        status = this.status ?: false,
        statusCode = this.statusCode ?: 0,
        supportWhatsappNumber = this.support_whatsapp_number ?: "",
        todayClicks = this.today_clicks ?: 0,
        topLocation = this.top_location ?: "",
        topSource = this.top_source ?: "",
        totalClicks = this.total_clicks ?: 0,
        totalLinks = this.total_links ?: 0,
    )
}