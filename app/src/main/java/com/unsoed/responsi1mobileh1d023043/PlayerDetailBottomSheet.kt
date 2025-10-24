package com.unsoed.responsi1mobileh1d023043

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.unsoed.responsi1mobileh1d023043.databinding.FragmentPlayerDetailBinding

class PlayerDetailBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentPlayerDetailBinding
    
    companion object {
        fun newInstance(
            playerName: String,
            birthDate: String,
            country: String,
            position: String
        ): PlayerDetailBottomSheet {
            val fragment = PlayerDetailBottomSheet()
            val args = Bundle().apply {
                putString("player_name", playerName)
                putString("birth_date", birthDate)
                putString("country", country)
                putString("position", position)
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayerDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        arguments?.let {
            binding.tvDetailName.text = it.getString("player_name")
            binding.tvDetailBirthDate.text = it.getString("birth_date")
            binding.tvDetailCountry.text = it.getString("country")
            binding.tvDetailPosition.text = it.getString("position")
        }
    }
}