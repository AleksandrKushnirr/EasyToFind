package com.kushnir.app.easytofind.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kushnir.app.easytofind.data.models.entity.Films
import com.kushnir.app.easytofind.data.room.type_converters.RoomConverters

@Database(entities = [Films::class], version = 1)
@TypeConverters(RoomConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun filmsDao(): FilmsDao
}