package com.vindove.pos.savesoul.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EmergencyContactEntities(
    val contactName: String,
    @PrimaryKey
    val contactNumber: String
)


