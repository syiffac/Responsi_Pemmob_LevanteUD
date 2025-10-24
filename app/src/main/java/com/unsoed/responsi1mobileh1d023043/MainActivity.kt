package com.unsoed.responsi1mobileh1d023043
import android.util.Log


import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.unsoed.responsi1mobileh1d023043.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnClubHistory.setOnClickListener {
            Log.d("MainActivity", "Club History button clicked")
            try {
                val intent = Intent(this, ClubHistoryActivity::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                Log.e("MainActivity", "Error starting ClubHistoryActivity", e)
            }
        }

        binding.btnHeadCoach.setOnClickListener {
            Log.d("MainActivity", "Head Coach button clicked")
            try {
                val intent = Intent(this, HeadCoachActivity::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                Log.e("MainActivity", "Error starting HeadCoachActivity", e)
            }
        }

        binding.btnTeamSquad.setOnClickListener {
            Log.d("MainActivity", "Team Squad button clicked")
            try {
                val intent = Intent(this, TeamSquadActivity::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                Log.e("MainActivity", "Error starting TeamSquadActivity", e)
            }
        }
    }
}