package com.kushnir.app.easytofind.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kushnir.app.easytofind.data.models.entity.Films

@Dao
interface FilmsDao {

    @Query("SELECT * FROM Films")
    suspend fun getAll(): List<Films>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(film: Films)

    @Query("DELETE FROM Films WHERE ID == :id")
    suspend fun removeById(id: Int)
}