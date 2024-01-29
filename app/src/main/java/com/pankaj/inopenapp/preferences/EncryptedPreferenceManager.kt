package com.pankaj.inopenapp.preferences

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import java.io.IOException
import java.security.GeneralSecurityException
import javax.inject.Inject

class EncryptedPreferenceManager @Inject constructor(private val context: Context) {
  
  private val sharedPreferencesName = "encrypted_shared_preferences"
  private val masterKeyAlias = "master_key_alias"
  
  private val masterKey: MasterKey by lazy {
    createMasterKey()
  }
  
  private fun createMasterKey(): MasterKey {
    return try {
      MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()
    } catch (e: IOException) {
      throw RuntimeException("Failed to create master key", e)
    } catch (e: GeneralSecurityException) {
      throw RuntimeException("Failed to create master key", e)
    }
  }
  
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
