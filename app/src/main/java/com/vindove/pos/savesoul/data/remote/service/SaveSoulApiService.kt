package com.vindove.pos.savesoul.data.remote.service

import com.vindove.pos.savesoul.data.remote.dto.request.SOSRequest
import com.vindove.pos.savesoul.util.BaseApiResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface SaveSoulApiService {

    @POST("create")
    suspend fun sendSOS(@Body request: SOSRequest) : BaseApiResponse<SOSRequest>
}