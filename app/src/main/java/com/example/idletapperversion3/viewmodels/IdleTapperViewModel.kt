package com.example.idletapperversion3.viewmodels

import androidx.lifecycle.*
import com.example.idletapperversion3.savedata.SaveData
import com.example.idletapperversion3.savedata.SaveDataDao
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class IdleTapperViewModel(private val saveDataDao: SaveDataDao) : ViewModel() {

    private var _saveData: MutableLiveData<SaveData> = getSave() as MutableLiveData<SaveData>
    val saveData: LiveData<SaveData>
        get() = _saveData


    private fun updateSave(saveData: SaveData) {
        viewModelScope.launch {
            saveDataDao.update(saveData)
        }
    }

    //change function to take an integer as input if support for multiple
    //saves added
    private fun getSave(): LiveData<SaveData> {
        lateinit var save: LiveData<SaveData>
        viewModelScope.launch {
            save = saveDataDao.getSaveData(1).asLiveData()
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

    private fun updateSaveData(taps: Int, prestige: Float,
                               tapPower: Int, idlePower: Int,
                               tapUpgradeSmall: Int, tapUpgradeMed: Int,
                               tapUpgradeBig: Int, idleUpgradeSmall: Int,
                               idleUpgradeMed: Int, idleUpgradeBig: Int) {
        val save = createSaveData(taps, prestige, tapPower, idlePower,
            tapUpgradeSmall, tapUpgradeMed, tapUpgradeBig,
            idleUpgradeSmall, idleUpgradeMed, idleUpgradeBig)
        _saveData.postValue(save)
    }

    fun save() {
        if(_saveData.value != null)
        {
            updateSave(_saveData.value!!)
        }
    }

    /*
    resets the save data. Make sure prestige and tapPower are set to 1 and not 0,
    as setting either value to zero will prevent taps being gained
     */
    fun reset() {
        updateSaveData(0, 1f, 1, 0, 0,
            0, 0, 0, 0, 0)
    }

    fun tapping() {
        val data: SaveData
        if(_saveData.value != null) {
            data = _saveData.value!!
            updateSaveData(
                (data.taps + data.tapPower * data.prestige).roundToInt(),
                data.prestige, data.tapPower, data.idlePower, data.tapUpgradeSmall,
                data.tapUpgradeMed, data.tapUpgradeBig, data.idleUpgradeSmall,
                data.idleUpgradeMed, data.idleUpgradeBig)
        }
    }

    fun idling() {
        val data: SaveData
        if(_saveData.value != null) {
            data = _saveData.value!!
            updateSaveData((data.taps + data.idlePower * data.prestige).roundToInt(),
                data.prestige, data.tapPower, data.idlePower, data.tapUpgradeSmall,
                data.tapUpgradeMed, data.tapUpgradeBig, data.idleUpgradeSmall,
                data.idleUpgradeMed, data.idleUpgradeBig)
        }
    }
//  Not sure if the line break at upgradePowerBoost was intentional - but generally if you are going
//  to line break you should break for each parameter in the function signature and indent right
    fun upgrade(upgradeIndex: Int, baseCost: Int, costIncreaseFactor: Float,
        upgradePowerBoost: Int) {
        val data: SaveData
        if(_saveData.value != null) {
            data = _saveData.value!!
            var cost = baseCost
            var upgradeLevel = 0
            when(upgradeIndex) {
                0 -> upgradeLevel = data.tapUpgradeSmall
                1 -> upgradeLevel = data.tapUpgradeMed
                2 -> upgradeLevel = data.tapUpgradeBig
                3 -> upgradeLevel = data.idleUpgradeSmall
                4 -> upgradeLevel = data.idleUpgradeMed
                5 -> upgradeLevel = data.idleUpgradeBig
            }
            // No need for a while loop! You just need to raise the cost factor to the upgradeLevel
            cost = (cost * Math.pow(costIncreaseFactor.toDouble(), upgradeIndex.toDouble())).roundToInt()
//            var x = 0
//            while (x < upgradeLevel) {
//                cost = (cost * costIncreaseFactor).roundToInt()
//                x++
//            }
            //update saveData based on which button was clicked

//          This function is much too long, and does probably on the order of three different things
//          At the very least, the if/when combo below should be moved to another function. You might
//          find that the code is cleaner actually if you split out updateSaveData into multiple
//          smaller helper functions (saveTaps, savePrestige, etc).
            if(data.taps >= cost) {
                when(upgradeIndex) {
                    0 -> updateSaveData(data.taps - cost, data.prestige,
                        data.tapPower + upgradePowerBoost, data.idlePower,
                        data.tapUpgradeSmall + 1, data.tapUpgradeMed,
                        data.tapUpgradeBig, data.idleUpgradeSmall, data.idleUpgradeMed,
                        data.idleUpgradeBig)
                    1 -> updateSaveData(data.taps - cost, data.prestige,
                        data.tapPower + upgradePowerBoost, data.idlePower,
                        data.tapUpgradeSmall, data.tapUpgradeMed + 1,
                        data.tapUpgradeBig, data.idleUpgradeSmall, data.idleUpgradeMed,
                        data.idleUpgradeBig)
                    2 -> updateSaveData(data.taps - cost, data.prestige,
                        data.tapPower + upgradePowerBoost, data.idlePower,
                        data.tapUpgradeSmall, data.tapUpgradeMed,
                        data.tapUpgradeBig + 1, data.idleUpgradeSmall,
                        data.idleUpgradeMed, data.idleUpgradeBig)
                    3 -> updateSaveData(data.taps - cost, data.prestige,
                        data.tapPower, data.idlePower + upgradePowerBoost,
                        data.tapUpgradeSmall, data.tapUpgradeMed, data.tapUpgradeBig,
                        data.idleUpgradeSmall + 1, data.idleUpgradeMed,
                        data.idleUpgradeBig)
                    4 -> updateSaveData(data.taps - cost, data.prestige,
                        data.tapPower, data.idlePower + upgradePowerBoost,
                        data.tapUpgradeSmall, data.tapUpgradeMed, data.tapUpgradeBig,
                        data.idleUpgradeSmall, data.idleUpgradeMed + 1,
                        data.idleUpgradeBig)
                    5 -> updateSaveData(data.taps - cost, data.prestige,
                        data.tapPower, data.idlePower + upgradePowerBoost,
                        data.tapUpgradeSmall, data.tapUpgradeMed, data.tapUpgradeBig,
                        data.idleUpgradeSmall, data.idleUpgradeMed,
                        data.idleUpgradeBig + 1)
                }
            }
        }
    }

    //returns the amount of prestige that would be gained from a prestige reset
    fun getPrestige(): Float {
        var prestige = 0f
        val data: SaveData
        if(_saveData.value != null) {
            data = _saveData.value!!

            if(data.taps < 1000){
                return 0f
            }

            prestige = (data.taps/1000).toFloat() * 0.05f
        }
        return prestige
    }

    fun gainPrestige() {
        val prestige = getPrestige()
        val data: SaveData
        if(_saveData.value != null) {
            data = _saveData.value!!
            updateSaveData(0, data.prestige + prestige,
                1, 0, 0,
                0, 0, 0,
                0, 0)
        }
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