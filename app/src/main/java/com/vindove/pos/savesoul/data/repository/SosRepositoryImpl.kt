package com.vindove.pos.savesoul.data.repository

import com.vindove.pos.savesoul.data.local.dao.EmergencyContactDao
import com.vindove.pos.savesoul.data.mappers.asDomain
import com.vindove.pos.savesoul.data.remote.dto.request.SOSRequest
import com.vindove.pos.savesoul.data.remote.service.SaveSoulApiService
import com.vindove.pos.savesoul.di.IoDispatcher
import com.vindove.pos.savesoul.domain.model.EmergencyContact
import com.vindove.pos.savesoul.domain.model.defaultEmergencyContact
import com.vindove.pos.savesoul.domain.repository.SosRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import com.vindove.pos.savesoul.util.Result
import javax.inject.Inject

class SosRepositoryImpl @Inject constructor(
    @IoDispatcher private  val ioDispatcher: CoroutineDispatcher,
    private val apiService: SaveSoulApiService,
    private val emergencyContactDao: EmergencyContactDao
) : SosRepository {

    override fun sendSOSRequest(request: SOSRequest): Flow<Result<SOSRequest>> {
        return flow {
            emit(Result.Loading)
            val resolvedDetails = apiService.sendSOS(request)
            emit(Result.Success(resolvedDetails.data))
        }.flowOn(ioDispatcher).catch {
            emit(Result.Failure(it))
        }
    }

    override fun fetchAllEmergencyContact(): Flow<List<EmergencyContact>> {
       return flow {
           val savedEmergencyContacts = emergencyContactDao.fetchAllEmergencyContact()
           emit(savedEmergencyContacts.asDomain().plus(defaultEmergencyContact))
       }.flowOn(ioDispatcher).catch {

       }
    }
}