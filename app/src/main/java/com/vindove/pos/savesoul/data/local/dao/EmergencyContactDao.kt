package com.vindove.pos.savesoul.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vindove.pos.savesoul.data.local.entities.EmergencyContactEntities


@Dao
interface EmergencyContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewEmergencyContact(emergencyContactEntities: EmergencyContactEntities)

    @Query("SELECT * FROM EMERGENCYCONTACTENTITIES")
    suspend fun fetchAllEmergencyContact(): List<EmergencyContactEntities>
}