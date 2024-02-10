package com.vindove.pos.savesoul.data.mappers

import com.vindove.pos.savesoul.data.local.entities.EmergencyContactEntities
import com.vindove.pos.savesoul.domain.model.EmergencyContact

fun EmergencyContactEntities.asDomain(): EmergencyContact {
    return EmergencyContact(
        contactName = this.contactName,
        contactNumber = this.contactNumber
    )
}

fun EmergencyContact.asEntity():EmergencyContactEntities{
    return EmergencyContactEntities(
        contactName = this.contactName,
        contactNumber = this.contactNumber
    )
}

fun List<EmergencyContactEntities>.asDomain(): List<EmergencyContact>{
    return this.map {
        it.asDomain()
    }
}