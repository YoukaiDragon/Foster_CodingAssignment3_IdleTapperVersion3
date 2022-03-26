package com.example.idletapperversion3.viewmodels

import androidx.lifecycle.*
import com.example.idletapperversion3.savedata.SaveData
import com.example.idletapperversion3.savedata.SaveDataDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class IdleTapperViewModel(private val saveDataDao: SaveDataDao) : ViewModel() {

    private var _saveData: MutableLiveData<SaveData> = getSave(1) as MutableLiveData<SaveData>
    val saveData: LiveData<SaveData>
        get() = _saveData

    private fun updateSave(saveData: SaveData) {
        viewModelScope.launch {
            saveDataDao.update(saveData)
        }
    }

    private fun getSave(id: Int): LiveData<SaveData> {
        lateinit var save: LiveData<SaveData>
        viewModelScope.launch {
            save = saveDataDao.getSaveData(id).asLiveData()
        }
        return save
    }

    private fun createSaveData(taps: Int, prestige: Float,
        tapPower: Int, idlePower: Int,
        tapUpgradeSmall: Int, tapUpgradeMed: Int,
        tapUpgradeBig: Int, idleUpgradeSmall: Int,
        idleUpgradeMed: Int, idleUpgradeBig: Int): SaveData {
        return SaveData(
            id = 1,
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

    fun updateSaveData(taps: Int, prestige: Float,
                       tapPower: Int, idlePower: Int,
                       tapUpgradeSmall: Int, tapUpgradeMed: Int,
                       tapUpgradeBig: Int, idleUpgradeSmall: Int,
                       idleUpgradeMed: Int, idleUpgradeBig: Int) {
        val save = createSaveData(taps, prestige, tapPower, idlePower,
            tapUpgradeSmall, tapUpgradeMed, tapUpgradeBig,
            idleUpgradeSmall, idleUpgradeMed, idleUpgradeBig)
        updateSave(save)
        _saveData.postValue(save)
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