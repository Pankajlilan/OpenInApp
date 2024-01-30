package com.pankaj.inopenapp.domain.model

data class Link(
    val app: String,
    val createdAt: String,
    val domainId: String,
    val isFavourite: Boolean,
    val originalImage: String,
    val smartLink: String,
    val thumbnail: String,
    val timesAgo: String,
    val title: String,
    val totalClicks: Int,
    val urlId: Int,
    val urlPrefix: String,
    val urlSuffix: String,
    val webLink: String
)