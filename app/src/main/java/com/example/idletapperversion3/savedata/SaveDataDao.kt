package com.example.idletapperversion3.savedata

import android.content.ClipData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SaveDataDao {

    @Query("SELECT * FROM savedata WHERE id = :id")
    fun getSaveData(id: Int): Flow<SaveData>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(saveData: SaveData)

    @Update
    suspend fun update(saveData: SaveData)

    @Query("DELETE FROM savedata")
    fun deleteSaveData()


}