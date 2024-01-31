package com.pankaj.inopenapp.preferences

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import javax.inject.Inject

class EncryptedPreferenceManager @Inject constructor(private val context: Context) {
  
  private val sharedPreferencesName = "encrypted_shared_preferences"
  private val masterKeyAlias = "master_key_alias"
  
  private val encryptedSharedPreferences by lazy {
    EncryptedSharedPreferences.create(
      sharedPreferencesName,
      masterKeyAlias,
      context,
      EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
      EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
  }
  
  fun saveString(key: String, value: String) {
    encryptedSharedPreferences.edit().putString(key, value).apply()
  }
  
  fun getString(key: String, defaultValue: String = ""): String {
    return encryptedSharedPreferences.getString(key, defaultValue) ?: defaultValue
  }
  
}
