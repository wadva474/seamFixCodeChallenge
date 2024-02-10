package com.vindove.pos.savesoul.util

import kotlinx.serialization.Serializable

@Serializable
data class BaseApiResponse<T>(
    val status: Boolean,
    val message: String,
    val data: T
)