package com.pankaj.inopenapp.domain.use_case

import com.pankaj.inopenapp.common.Resource
import com.pankaj.inopenapp.data.model.toDashboard
import com.pankaj.inopenapp.domain.model.Dashboard
import com.pankaj.inopenapp.domain.repository.DashboardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetDashboardUseCase @Inject constructor(private val repository: DashboardRepository) {
  operator fun invoke(): Flow<Resource<Dashboard>> = flow {
    try {
      emit(Resource.Loading())
//    Converting response dataDTO transfer object to normal object and implemented mapping
//    Catching and handling required Exceptions
      val data = repository.getDashboardData()
      val domainData = data.toDashboard()
      emit(Resource.Success(data = domainData))
    } catch (e: HttpException) {
      emit(Resource.Error(message = e.localizedMessage ?: "An Unknown error occurred"))
    } catch (e: IOException) {
      emit(Resource.Error(message = e.localizedMessage ?: "Check Connectivity"))
    } catch (e: Exception) {
      emit(Resource.Error(message = e.localizedMessage ?: ("" + e.message)))
    }
  }
  
  
}