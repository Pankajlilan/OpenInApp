package com.pankaj.inopenapp.common

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.View
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun View.openWhatsAppChat(toNumber: String) {
  val url = "https://api.whatsapp.com/send?phone=$toNumber"
  try {
    context.packageManager.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES)
    context.startActivity(Intent(Intent.ACTION_VIEW).apply { data = Uri.parse(url) })
  } catch (e: PackageManager.NameNotFoundException) {
    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
  }
}

fun String.convertDateToFloat(): Float {
  val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
  
  try {
    // Parse the string to a Date object
    val date = dateFormat.parse(this)
    
    // Convert the Date to a Calendar
    val calendar = Calendar.getInstance()
    calendar.time = date
    
    // Extract the day of the month and convert it to a float
    val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
    return dayOfMonth.toFloat()
    
  } catch (e: Exception) {
    // Handle parsing or formatting errors
    e.printStackTrace()
  }
  
  // Return a default value or handle the error case
  return -1.0f
}

fun String.convertDateToTitleFormat(): String {
  val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
  val outputFormat = SimpleDateFormat("dd MMM", Locale.getDefault())
  
  try {
    val date = inputFormat.parse(this)
    return outputFormat.format(date!!)
  } catch (e: Exception) {
    e.printStackTrace()
  }
  
  return ""
}


