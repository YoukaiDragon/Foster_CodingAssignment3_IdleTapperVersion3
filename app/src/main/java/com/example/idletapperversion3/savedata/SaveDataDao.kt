package com.example.idletapperversion3.savedata

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SaveDataDao {

    @Query("SELECT * FROM savedata WHERE id = 0")
    fun getSaveData(): Flow<SaveData>

    @Update
    suspend fun update(saveData: SaveData)
}