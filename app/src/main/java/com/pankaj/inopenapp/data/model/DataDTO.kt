package com.pankaj.inopenapp.data.model

data class DataDTO(
    val favourite_links: List<Any>,
    val overall_url_chart: OverallUrlChart,
    val recent_linkDTOS: List<LinkDTO>,
    val top_linkDTOS: List<LinkDTO>
)