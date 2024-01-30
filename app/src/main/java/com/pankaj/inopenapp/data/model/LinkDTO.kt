package com.pankaj.inopenapp.data.model

import com.pankaj.inopenapp.domain.model.Link

data class LinkDTO(
  val app: String?,
  val created_at: String?,
  val domain_id: String?,
  val is_favourite: Boolean?,
  val original_image: String?,
  val smart_link: String?,
  val thumbnail: String?,
  val times_ago: String?,
  val title: String?,
  val total_clicks: Int?,
  val url_id: Int?,
  val url_prefix: String?,
  val url_suffix: String?,
  val web_link: String?
)

fun LinkDTO.toLink(): Link {
  return Link(
    app = this.app ?: "",
    createdAt = this.created_at ?: "",
    domainId = this.domain_id ?: "",
    isFavourite = this.is_favourite ?: false,
    originalImage = this.original_image ?: "",
    smartLink = this.smart_link ?: "",
    thumbnail = this.thumbnail ?: "",
    timesAgo = this.times_ago ?: "",
    title = this.title ?: "",
    totalClicks = this.total_clicks ?: 0,
    urlId = this.url_id ?: 0,
    urlPrefix = this.url_prefix ?: "",
    urlSuffix = this.url_suffix ?: "",
    webLink = this.web_link ?: ""
  )
}