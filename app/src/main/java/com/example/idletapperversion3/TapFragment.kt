package com.example.idletapperversion3

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.idletapperversion3.databinding.FragmentTapBinding
import com.example.idletapperversion3.savedata.SaveData
import com.example.idletapperversion3.viewmodels.IdleTapperViewModel
import com.example.idletapperversion3.viewmodels.IdleTapperViewModelFactory

class TapFragment : Fragment() {

    private val viewModel: IdleTapperViewModel by activityViewModels {
        IdleTapperViewModelFactory(
            (activity?.application as IdleTapperApplication).database.saveDataDao()
        )
    }

    private lateinit var mainActivity: MainActivity

    private var _binding: FragmentTapBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainActivity = this.activity as MainActivity
        viewModel.saveData.observe(viewLifecycleOwner, { save ->
            updateUI(save)
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

        //start function to generate idle taps
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(object : Runnable {
            override fun run() {
                idleTap()
                handler.postDelayed(this, 1000)//1/second
            }
        }, 0)
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
        if(saveData != null){
            binding.tapCounter.text = getString(R.string.tap_counter, saveData.taps)
            binding.tapPowerText.text = getString(R.string.tap_power_display, saveData.tapPower)
            binding.idlePowerText.text = getString(R.string.idle_power_display, saveData.idlePower)
            binding.prestigeText.text = getString(R.string.prestige_display, saveData.prestige)
        } else {
            // I haven't given this a full test, but you need something like this to avoid the
            // scenario where your strings are not correctly initialized on first load. This might
            // not be the most optimal solution, but was the first approach I came across.
            viewModel.reset()
        }
    }

    //function called every second by handler to passively increase tapCount
    private fun idleTap() {
        viewModel.idling()
    }

}