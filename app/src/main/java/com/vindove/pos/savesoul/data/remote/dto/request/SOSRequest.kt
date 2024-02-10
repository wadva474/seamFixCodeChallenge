package com.vindove.pos.savesoul.data.remote.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val longitude: String,
    val latitude: String
)


@Serializable
data class SOSRequest(
    val phoneNumbers: List<String>,
    val image: String,
    val location: Location
)