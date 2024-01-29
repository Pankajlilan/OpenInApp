package com.pankaj.inopenapp.domain.use_case

import com.pankaj.inopenapp.common.Resource
import com.pankaj.inopenapp.domain.repository.EmployeeSearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchEmployeeUseCase @Inject constructor(private val repository: EmployeeSearchRepository) {

    operator fun invoke(): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            emit(Resource.Success(data = repository.getBearerToken()))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "An Unknown error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Check Connectivity"))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.localizedMessage ?: ("" + e.message)))
        }
    }


}