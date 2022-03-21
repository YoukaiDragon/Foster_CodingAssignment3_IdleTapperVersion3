package com.example.idletapperversion3.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.idletapperversion3.savedata.SaveDataDao
import java.lang.IllegalArgumentException

class IdleTapperViewModel(private val saveDataDao: SaveDataDao) : ViewModel() {

}

class IdleTapperViewModelFactory(private val saveDataDao: SaveDataDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(IdleTapperViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return IdleTapperViewModel(saveDataDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}