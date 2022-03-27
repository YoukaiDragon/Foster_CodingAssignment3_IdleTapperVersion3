package com.example.idletapperversion3.savedata

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface SaveDataDao {

    @Query("SELECT * FROM savedata WHERE id = :id")
    fun getSaveData(id: Int): Flow<SaveData>

    @Update
    suspend fun update(saveData: SaveData)


}