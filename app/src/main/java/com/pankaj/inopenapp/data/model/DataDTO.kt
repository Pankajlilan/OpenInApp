package com.pankaj.inopenapp.data.model

import com.pankaj.inopenapp.domain.model.Data

data class DataDTO(
  val favourite_links: MutableList<LinkDTO>?,
  val overall_url_chart: Map<String, Int>,
  val recent_links: MutableList<LinkDTO>?,
  val top_links: MutableList<LinkDTO>?
)

fun DataDTO.toData(): Data {
  return Data(
    favouriteLinks = this.favourite_links?.map { it.toLink() }?.toMutableList() ?: mutableListOf(),
    overallUrlChart = this.overall_url_chart,
    recentLinks = this.recent_links?.map { it.toLink() }?.toMutableList() ?: mutableListOf(),
    topLinks = this.top_links?.map { it.toLink() }?.toMutableList() ?: mutableListOf()
  )
}