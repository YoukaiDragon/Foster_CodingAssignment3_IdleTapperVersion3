package com.example.idletapperversion3

import  android.os.Bundle
import android.util.Log
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

    private val TAG = "Store Fragment"

    private val viewModel: IdleTapperViewModel by activityViewModels {
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
        viewModel.saveData.observe(viewLifecycleOwner, {save -> updateUI(save)})
        _binding = FragmentStoreBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.exitStoreButton.setOnClickListener {
            val action = StoreFragmentDirections.actionStoreFragmentToTapFragment()
            findNavController().navigate(action)
        }

        binding.smallTapButton.setOnClickListener {
            val item = storeItemList[SMALL_TAP]
            viewModel.upgrade(SMALL_TAP, item.baseCost, item.costIncreaseFactor,
                item.upgradePowerBoost, item.idle)
        }
        binding.medTapButton.setOnClickListener {
            val item = storeItemList[MED_TAP]
            viewModel.upgrade(MED_TAP, item.baseCost, item.costIncreaseFactor,
                item.upgradePowerBoost, item.idle)
        }
        binding.bigTapButton.setOnClickListener {
            val item = storeItemList[BIG_TAP]
            viewModel.upgrade(BIG_TAP, item.baseCost, item.costIncreaseFactor,
                item.upgradePowerBoost, item.idle)
        }
        binding.smallIdleButton.setOnClickListener {
            val item = storeItemList[SMALL_IDLE]
            viewModel.upgrade(SMALL_IDLE, item.baseCost, item.costIncreaseFactor,
                item.upgradePowerBoost, item.idle)
        }
        binding.medIdleButton.setOnClickListener {
            val item = storeItemList[MED_IDLE]
            viewModel.upgrade(MED_IDLE, item.baseCost, item.costIncreaseFactor,
                item.upgradePowerBoost, item.idle)
        }
        binding.bigIdleButton.setOnClickListener {
            val item = storeItemList[BIG_IDLE]
            viewModel.upgrade(BIG_IDLE, item.baseCost, item.costIncreaseFactor,
                item.upgradePowerBoost, item.idle)
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

    private fun updateUI(save: SaveData?) {
        if(save != null) {
            binding.tapCounter.text = getString(R.string.tap_counter, save.taps)
            binding.smallTapText.text = getString(R.string.small_tapper, save.tapUpgradeSmall
                ,getUpgradeCost(SMALL_TAP, save))
            binding.medTapText.text = getString(R.string.med_tapper, save.tapUpgradeMed
                ,getUpgradeCost(MED_TAP, save))
            binding.bigTapText.text = getString(R.string.big_tapper, save.tapUpgradeBig
                ,getUpgradeCost(BIG_TAP, save))
            binding.smallIdleText.text = getString(R.string.small_idler, save.idleUpgradeSmall
                ,getUpgradeCost(SMALL_IDLE, save))
            binding.medIdleText.text = getString(R.string.med_idler, save.idleUpgradeMed
                ,getUpgradeCost(MED_IDLE, save))
            binding.bigIdleText.text = getString(R.string.big_idler, save.idleUpgradeBig
                ,getUpgradeCost(BIG_IDLE, save))
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
