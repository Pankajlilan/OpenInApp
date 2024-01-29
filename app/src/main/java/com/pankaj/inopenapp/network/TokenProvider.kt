package com.pankaj.inopenapp.network

import android.content.SharedPreferences
import com.pankaj.inopenapp.common.Constants
import com.pankaj.inopenapp.preferences.EncryptedPreferenceManager

class TokenProvider(private val encryptedPreferenceManager: EncryptedPreferenceManager) {
  
  fun getToken(): String {
    return encryptedPreferenceManager.getString(Constants.TOKEN, "") ?: ""
  }
}


