package com.example.idletapperversion3.viewmodels

import androidx.lifecycle.*
import com.example.idletapperversion3.savedata.SaveData
import com.example.idletapperversion3.savedata.SaveDataDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class IdleTapperViewModel(private val saveDataDao: SaveDataDao) : ViewModel() {
    private fun updateSave(saveData: SaveData) {
        viewModelScope.launch {
            saveDataDao.update(saveData)
        }
    }

    private fun insertSave(saveData: SaveData) {
        viewModelScope.launch {
            saveDataDao.insert(saveData)
        }
    }

    private fun getSave(id: Int): Flow<SaveData> {
        lateinit var save: Flow<SaveData>
        viewModelScope.launch {
            save = saveDataDao.getSaveData(id)
        }
        return save
    }

    private fun resetSave(saveData: SaveData) {
        viewModelScope.launch{
            saveDataDao.deleteSaveData()
            saveDataDao.insert(saveData)
        }
    }

    private fun createSaveData(taps: Int, prestige: Float,
        tapPower: Int, idlePower: Int,
        tapUpgradeSmall: Int, tapUpgradeMed: Int,
        tapUpgradeBig: Int, idleUpgradeSmall: Int,
        idleUpgradeMed: Int, idleUpgradeBig: Int): SaveData {
        return SaveData(
            taps = taps,
            prestige = prestige,
            tapPower = tapPower,
            idlePower = idlePower,
            tapUpgradeSmall =  tapUpgradeSmall,
            tapUpgradeMed = tapUpgradeMed,
            tapUpgradeBig = tapUpgradeBig,
            idleUpgradeSmall = idleUpgradeSmall,
            idleUpgradeMed = idleUpgradeMed,
            idleUpgradeBig = idleUpgradeBig
        )
    }

    fun insertSaveData(taps: Int, prestige: Float,
                       tapPower: Int, idlePower: Int,
                       tapUpgradeSmall: Int, tapUpgradeMed: Int,
                       tapUpgradeBig: Int, idleUpgradeSmall: Int,
                       idleUpgradeMed: Int, idleUpgradeBig: Int) {
        val save = createSaveData(taps, prestige, tapPower, idlePower,
            tapUpgradeSmall, tapUpgradeMed, tapUpgradeBig,
            idleUpgradeSmall, idleUpgradeMed, idleUpgradeBig)
        insertSave(save)
    }

    fun updateSaveData(taps: Int, prestige: Float,
                       tapPower: Int, idlePower: Int,
                       tapUpgradeSmall: Int, tapUpgradeMed: Int,
                       tapUpgradeBig: Int, idleUpgradeSmall: Int,
                       idleUpgradeMed: Int, idleUpgradeBig: Int) {
        val save = createSaveData(taps, prestige, tapPower, idlePower,
            tapUpgradeSmall, tapUpgradeMed, tapUpgradeBig,
            idleUpgradeSmall, idleUpgradeMed, idleUpgradeBig)
        updateSave(save)
    }

    fun createNewSave() {
        val save = createSaveData(0,1f,1,0,0,
        0,0,0,0,0)
        resetSave(save)
    }

    fun getSaveData(id: Int): LiveData<SaveData> {
        return getSave(id).asLiveData()
    }
}

class IdleTapperViewModelFactory(private val saveDataDao: SaveDataDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(IdleTapperViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return IdleTapperViewModel(saveDataDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}