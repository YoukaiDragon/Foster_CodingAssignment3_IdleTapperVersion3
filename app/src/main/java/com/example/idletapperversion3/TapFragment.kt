package com.example.idletapperversion3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.example.idletapperversion3.databinding.FragmentTapBinding
import com.example.idletapperversion3.savedata.SaveData
import com.example.idletapperversion3.viewmodels.IdleTapperViewModel
import com.example.idletapperversion3.viewmodels.IdleTapperViewModelFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last

class TapFragment : Fragment() {

    private val viewModel: IdleTapperViewModel by activityViewModels {
        IdleTapperViewModelFactory(
            (activity?.application as IdleTapperApplication).database.saveDataDao()
        )
    }

    lateinit var save: LiveData<SaveData>
    lateinit var mainActivity: MainActivity

    private var _binding: FragmentTapBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainActivity = this.activity as MainActivity
        _binding = FragmentTapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.button.setOnClickListener {
            val action = TapFragmentDirections.actionTapFragmentToStoreFragment()
            findNavController().navigate(action)
        }
       loadSave()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadSave() {
        save = viewModel.getSaveData(0)
        bind(save.value)
    }

    private fun saveData() {

    }

    private fun createSave() {
        viewModel.insertSaveData(0,1f,1,0,0,
        0,0,0,0,0)
    }

    private fun bind(saveData: SaveData?){
        if(saveData != null) {
            mainActivity.taps = saveData.taps
            mainActivity.prestige = saveData.prestige
            mainActivity.tapPower = saveData.tapPower
            mainActivity.idlePower = saveData.idlePower
            mainActivity.tapUpgradeSmall = saveData.tapUpgradeSmall
            mainActivity.tapUpgradeMed = saveData.tapUpgradeMed
            mainActivity.tapUpgradeBig = saveData.tapUpgradeBig
            mainActivity.idleUpgradeSmall = saveData.idleUpgradeSmall
            mainActivity.idleUpgradeMed = saveData.idleUpgradeMed
            mainActivity.idleUpgradeBig = saveData.idleUpgradeBig
        }
    }

}