package com.pankaj.inopenapp.domain.model

data class Data(
    val favouriteLinks: MutableList<Link>,
    val overallUrlChart: Map<String, Int>,
    val recentLinks: MutableList<Link>,
    val topLinks: MutableList<Link>
)