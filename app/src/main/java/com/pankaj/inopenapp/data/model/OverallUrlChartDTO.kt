package com.pankaj.inopenapp.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.pankaj.inopenapp.domain.model.OverallUrlChart

data class OverallUrlChartDTO(
  
  @SerializedName("overall_url_chart")
  @Expose
  val overall_url_chart: Map<String, Int>?
)

fun OverallUrlChartDTO.toOverallUrlChart(): OverallUrlChart {
  return OverallUrlChart(chartData = this.overall_url_chart ?: emptyMap())
}