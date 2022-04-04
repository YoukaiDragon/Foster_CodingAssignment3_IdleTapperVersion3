package com.example.idletapperversion3

import  android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.idletapperversion3.databinding.FragmentStoreBinding
import com.example.idletapperversion3.savedata.SaveData
import com.example.idletapperversion3.storeData.Datasource
import com.example.idletapperversion3.viewmodels.IdleTapperViewModel
import com.example.idletapperversion3.viewmodels.IdleTapperViewModelFactory
import kotlin.math.roundToInt

class StoreFragment : Fragment() {

    private val viewModel: IdleTapperViewModel by activityViewModels {
        IdleTapperViewModelFactory(
            (activity?.application as IdleTapperApplication).database.saveDataDao()
        )
    }

    // Better to store these in an enum
    private val SMALL_TAP = 0
    private val MED_TAP = 1
    private val BIG_TAP = 2
    private val SMALL_IDLE = 3
    private val MED_IDLE = 4
    private val BIG_IDLE = 5

    private val storeItemList = Datasource().loadData()

    private var _binding: FragmentStoreBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainActivity = this.activity as MainActivity
        viewModel.saveData.observe(viewLifecycleOwner, { save -> updateUI(save) })
        _binding = FragmentStoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.exitStoreButton.setOnClickListener {
            val action = StoreFragmentDirections.actionStoreFragmentToTapFragment()
            findNavController().navigate(action)
        }

        binding.smallTapButton.setOnClickListener {
            val item = storeItemList[SMALL_TAP]
            viewModel.upgrade(SMALL_TAP, item.baseCost, item.costIncreaseFactor,
                item.upgradePowerBoost)
        }
        binding.medTapButton.setOnClickListener {
            val item = storeItemList[MED_TAP]
            viewModel.upgrade(MED_TAP, item.baseCost, item.costIncreaseFactor,
                item.upgradePowerBoost)
        }
        binding.bigTapButton.setOnClickListener {
            val item = storeItemList[BIG_TAP]
            viewModel.upgrade(BIG_TAP, item.baseCost, item.costIncreaseFactor,
                item.upgradePowerBoost)
        }
        binding.smallIdleButton.setOnClickListener {
            val item = storeItemList[SMALL_IDLE]
            viewModel.upgrade(SMALL_IDLE, item.baseCost, item.costIncreaseFactor,
                item.upgradePowerBoost)
        }
        binding.medIdleButton.setOnClickListener {
            val item = storeItemList[MED_IDLE]
            viewModel.upgrade(MED_IDLE, item.baseCost, item.costIncreaseFactor,
                item.upgradePowerBoost)
        }
        binding.bigIdleButton.setOnClickListener {
            val item = storeItemList[BIG_IDLE]
            viewModel.upgrade(BIG_IDLE, item.baseCost, item.costIncreaseFactor,
                item.upgradePowerBoost)
        }

        binding.resetButton.setOnClickListener {
            val builder = AlertDialog.Builder(mainActivity)
            builder.setTitle("Reset Progress")
            builder.setMessage("Are you sure you want to reset all progress?")
            builder.setPositiveButton("Yes") { _, _ -> reset() }
            builder.setNegativeButton("No") { dialog, _ -> dialog.cancel() }
            builder.show()
        }

        binding.prestigeButton.setOnClickListener {
            val prestige = viewModel.getPrestige()
            val builder = AlertDialog.Builder(mainActivity)
            builder.setTitle("Prestige Reset")
            builder.setMessage("Do you want to reset taps and upgrades to gain " +
                    prestige + " prestige?")
            builder.setPositiveButton("Yes") { _, _ -> gainPrestige()}
            builder.setNegativeButton("No") {dialog, _ -> dialog.cancel()}
            builder.show()
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.save()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateUI(saveData: SaveData?) {
        if(saveData != null) {
            binding.tapCounter.text = getString(R.string.tap_counter, saveData.taps)
            binding.smallTapText.text = getString(R.string.small_tapper, saveData.tapUpgradeSmall
                ,getUpgradeCost(SMALL_TAP, saveData))
            binding.medTapText.text = getString(R.string.med_tapper, saveData.tapUpgradeMed
                ,getUpgradeCost(MED_TAP, saveData))
            binding.bigTapText.text = getString(R.string.big_tapper, saveData.tapUpgradeBig
                ,getUpgradeCost(BIG_TAP, saveData))
            binding.smallIdleText.text = getString(R.string.small_idler, saveData.idleUpgradeSmall
                ,getUpgradeCost(SMALL_IDLE, saveData))
            binding.medIdleText.text = getString(R.string.med_idler, saveData.idleUpgradeMed
                ,getUpgradeCost(MED_IDLE, saveData))
            binding.bigIdleText.text = getString(R.string.big_idler, saveData.idleUpgradeBig
                ,getUpgradeCost(BIG_IDLE, saveData))
            binding.tapPowerDisplay.text = getString(R.string.tap_power_display, saveData.tapPower)
            binding.idlePowerDisplay.text = getString(R.string.idle_power_display, saveData.idlePower)
            binding.prestigeDisplay.text = getString(R.string.prestige_display, saveData.prestige)
        }
    }

    //determines the costs of an upgrade
    private fun getUpgradeCost(index: Int, save: SaveData?): Int {
        val item = storeItemList[index]
        var cost = item.baseCost
        if(save != null){

            var upgradeLevel = 0
            when(index){
                SMALL_TAP -> upgradeLevel = save.tapUpgradeSmall
                MED_TAP -> upgradeLevel = save.tapUpgradeMed
                BIG_TAP -> upgradeLevel = save.tapUpgradeBig
                SMALL_IDLE -> upgradeLevel = save.idleUpgradeSmall
                MED_IDLE -> upgradeLevel = save.idleUpgradeMed
                BIG_IDLE -> upgradeLevel = save.idleUpgradeBig
            }
            var x = 0

            //each upgrade level cost is the previous level cost * costIncreaseFactor
            while (x < upgradeLevel) {
                cost = (cost * item.costIncreaseFactor).roundToInt()
                x++
            }
        }
        return cost
    }

    private fun reset() {
        viewModel.reset()
    }

    private fun gainPrestige() {
        viewModel.gainPrestige()
    }
}
