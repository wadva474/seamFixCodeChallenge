package com.vindove.pos.savesoul.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vindove.pos.savesoul.data.local.dao.EmergencyContactDao
import com.vindove.pos.savesoul.data.local.entities.EmergencyContactEntities

@Database(entities = [EmergencyContactEntities::class], version = 1, exportSchema = false)
abstract class SaveSoulDatabase : RoomDatabase() {
    abstract fun emergencyContactDao(): EmergencyContactDao
}