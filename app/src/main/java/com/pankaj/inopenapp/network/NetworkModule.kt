package com.pankaj.inopenapp.network


import com.pankaj.inopenapp.common.Constants
import com.pankaj.inopenapp.data.remote.DashboardAPI
import com.pankaj.inopenapp.data.repository.DashboardRepositoryImpl
import com.pankaj.inopenapp.domain.repository.DashboardRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltModules {
  
  // Define an interceptor to add the bearer token to requests
  private class AuthInterceptor(private val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
      val originalRequest = chain.request()
      val newRequest = originalRequest.newBuilder()
        .header("Authorization", "Bearer $token")
        .build()
      return chain.proceed(newRequest)
    }
  }
  
  @Singleton
  @Provides
  fun provideOkHttpClient(tokenProvider: TokenProvider): OkHttpClient {
    val token = tokenProvider.getToken()
    return OkHttpClient.Builder()
      .addInterceptor(AuthInterceptor(token.trim()))
      .build()
  }
  
  @Singleton
  @Provides
  fun provideInOpenAppAPI(okHttpClient: OkHttpClient): DashboardAPI {
    return Retrofit.Builder()
      .baseUrl(Constants.BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .client(okHttpClient)
      .build()
      .create(DashboardAPI::class.java)
  }
  
  
  @Provides
  fun provideDashboardData(dashboardAPI: DashboardAPI): DashboardRepository {
    return DashboardRepositoryImpl(dashboardAPI)
  }
}

