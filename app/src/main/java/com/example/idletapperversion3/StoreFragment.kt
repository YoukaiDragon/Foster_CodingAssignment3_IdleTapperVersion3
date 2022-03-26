package com.example.idletapperversion3

import  android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.idletapperversion3.databinding.FragmentStoreBinding
import com.example.idletapperversion3.model.StoreItem
import com.example.idletapperversion3.storeData.Datasource
import com.example.idletapperversion3.viewmodels.IdleTapperViewModel
import com.example.idletapperversion3.viewmodels.IdleTapperViewModelFactory
import kotlin.math.roundToInt

class StoreFragment : Fragment() {

    private val TAG = "Store Fragment"

    val viewModel: IdleTapperViewModel by activityViewModels {
        IdleTapperViewModelFactory(
            (activity?.application as IdleTapperApplication).database.saveDataDao()
        )
    }

    private val SMALL_TAP = 0
    private val MED_TAP = 1
    private val BIG_TAP = 2
    private val SMALL_IDLE = 3
    private val MED_IDLE = 4
    private val BIG_IDLE = 5

    val storeItemList = Datasource().loadData()

    private var _binding: FragmentStoreBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = this.activity as MainActivity
        _binding = FragmentStoreBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.smallTapButton.setOnClickListener {
            upgrade(SMALL_TAP)
        }
        binding.medTapButton.setOnClickListener {
            upgrade(MED_TAP)
        }
        binding.bigTapButton.setOnClickListener {
            upgrade(BIG_TAP)
        }
        binding.smallIdleButton.setOnClickListener {
            upgrade(SMALL_IDLE)
        }
        binding.medIdleButton.setOnClickListener {
            upgrade(MED_IDLE)
        }
        binding.bigIdleButton.setOnClickListener {
            upgrade(BIG_IDLE)
        }

        updateUI()
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause")
        saveData()
        Log.i(TAG, "finished Saving")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateUI() {
        binding.tapCounter.text = getString(R.string.tap_counter, mainActivity.taps)
        binding.smallTapText.text = getString(R.string.small_tapper, mainActivity.tapUpgradeSmall
            ,getUpgradeCost(SMALL_TAP))
        binding.medTapText.text = getString(R.string.med_tapper, mainActivity.tapUpgradeMed
            ,getUpgradeCost(MED_TAP))
        binding.bigTapText.text = getString(R.string.big_tapper, mainActivity.tapUpgradeBig
            ,getUpgradeCost(BIG_TAP))
        binding.smallIdleText.text = getString(R.string.small_idler, mainActivity.idleUpgradeSmall
            ,getUpgradeCost(SMALL_IDLE))
        binding.medIdleText.text = getString(R.string.med_idler, mainActivity.idleUpgradeMed
            ,getUpgradeCost(MED_IDLE))
        binding.bigIdleText.text = getString(R.string.big_idler, mainActivity.idleUpgradeBig
            ,getUpgradeCost(BIG_IDLE))
    }

    //determines the costs of an upgrade
    private fun getUpgradeCost(index: Int): Int {
        val item = storeItemList[index]
        var upgradeLevel = 0
        when(index){
            SMALL_TAP -> upgradeLevel = mainActivity.tapUpgradeSmall
            MED_TAP -> upgradeLevel = mainActivity.tapUpgradeMed
            BIG_TAP -> upgradeLevel = mainActivity.tapUpgradeBig
            SMALL_IDLE -> upgradeLevel = mainActivity.idleUpgradeSmall
            MED_IDLE -> upgradeLevel = mainActivity.idleUpgradeMed
            BIG_IDLE -> upgradeLevel = mainActivity.idleUpgradeBig
        }
        var cost = item.baseCost
        var x = 0

        //each upgrade level cost is the previous level cost * costIncreaseFactor
        while (x < upgradeLevel) {
            cost = (cost * item.costIncreaseFactor).roundToInt()
            x++
        }

        return cost
    }

    private fun upgrade(index: Int) {
        val item = storeItemList[index]
        var cost = getUpgradeCost(index)

        if(mainActivity.taps >= cost) {
            mainActivity.taps -= cost
            if(item.idle) {
                mainActivity.idlePower += item.upgradePowerBoost
            } else {
                mainActivity.tapPower += item.upgradePowerBoost
            }

            when(index){
                SMALL_TAP -> mainActivity.tapUpgradeSmall++
                MED_TAP -> mainActivity.tapUpgradeMed++
                BIG_TAP -> mainActivity.tapUpgradeBig++
                SMALL_IDLE -> mainActivity.idleUpgradeSmall++
                MED_IDLE -> mainActivity.idleUpgradeMed++
                BIG_IDLE -> mainActivity.idleUpgradeBig++
            }

            updateUI()
        }
    }

    private fun saveData() {
        viewModel.updateSaveData(mainActivity.taps, mainActivity.prestige, mainActivity.tapPower,
            mainActivity.idlePower, mainActivity.tapUpgradeSmall, mainActivity.tapUpgradeMed,
            mainActivity.tapUpgradeBig, mainActivity.idleUpgradeSmall, mainActivity.idleUpgradeMed,
            mainActivity.idleUpgradeBig)
    }
}