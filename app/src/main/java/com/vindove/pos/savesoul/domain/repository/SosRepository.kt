package com.vindove.pos.savesoul.domain.repository

import com.vindove.pos.savesoul.data.remote.dto.request.SOSRequest
import com.vindove.pos.savesoul.domain.model.EmergencyContact
import com.vindove.pos.savesoul.util.Result
import kotlinx.coroutines.flow.Flow

interface SosRepository {

    fun sendSOSRequest(request: SOSRequest): Flow<Result<SOSRequest>>

    fun fetchAllEmergencyContact():Flow<List<EmergencyContact>>
}