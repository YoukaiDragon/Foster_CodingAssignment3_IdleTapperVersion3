package com.example.idletapperversion3

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.idletapperversion3.databinding.FragmentTapBinding
import com.example.idletapperversion3.savedata.SaveData
import com.example.idletapperversion3.viewmodels.IdleTapperViewModel
import com.example.idletapperversion3.viewmodels.IdleTapperViewModelFactory

class TapFragment : Fragment() {

    private val TAG = "TapFragment"

    val viewModel: IdleTapperViewModel by activityViewModels {
        IdleTapperViewModelFactory(
            (activity?.application as IdleTapperApplication).database.saveDataDao()
        )
    }

    lateinit var save: LiveData<SaveData>
    private lateinit var mainActivity: MainActivity

    private var _binding: FragmentTapBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainActivity = this.activity as MainActivity
        viewModel.saveData.observe(viewLifecycleOwner, Observer {save ->
            bind(save)
        })
        _binding = FragmentTapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.shopButton.setOnClickListener {
            val action = TapFragmentDirections.actionTapFragmentToStoreFragment()
            findNavController().navigate(action)
        }

        binding.tapButton.setOnClickListener {
            viewModel.tapping()
        }

        //loadSave()
        //initialize UI values
        updateUI()
    }

    override fun onPause() {
        super.onPause()
        viewModel.save()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
        updateUI()
    }

    private fun updateUI() {
        binding.tapCounter.text = getString(R.string.tap_counter, mainActivity.taps)
    }

    private fun updateTaps(taps: Int) {
        binding.tapCounter.text = getString(R.string.tap_counter, taps)
    }

}