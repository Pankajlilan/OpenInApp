package com.pankaj.inopenapp.network

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.pankaj.inopenapp.preferences.EncryptedPreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
  
  @Provides
  @Singleton
  fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
    return context.getSharedPreferences("YOUR_PREFERENCES_NAME", Context.MODE_PRIVATE)
  }
  
  @Provides
  @Singleton
  fun provideTokenProvider(encryptedPreferenceManager: EncryptedPreferenceManager): TokenProvider {
    return TokenProvider(encryptedPreferenceManager)
  }
  
  @Singleton
  @Provides
  fun provideEncryptedPreferenceManager(context: Context): EncryptedPreferenceManager {
    return EncryptedPreferenceManager(context)
  }
  
  @Provides
  @Singleton
  fun provideApplicationContext(application: Application): Context {
    return application.applicationContext
  }
}

