package com.vindove.pos.savesoul.domain.model

data class EmergencyContact(
    val contactName : String = "Emergency",
    val contactNumber : String = "991"
)

val defaultEmergencyContact = EmergencyContact(
    contactName = "Emergency",
    contactNumber = "991"
)