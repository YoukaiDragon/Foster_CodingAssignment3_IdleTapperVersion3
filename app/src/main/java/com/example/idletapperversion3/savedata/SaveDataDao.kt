package com.example.idletapperversion3.savedata

import android.content.ClipData
import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SaveDataDao {

    @Query("SELECT * FROM savedata WHERE id = :id")
    fun getSaveData(id: Int): Flow<SaveData>

    @Update
    suspend fun update(saveData: SaveData)


}