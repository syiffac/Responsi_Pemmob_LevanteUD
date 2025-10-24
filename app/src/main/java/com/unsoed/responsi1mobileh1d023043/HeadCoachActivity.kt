package com.unsoed.responsi1mobileh1d023043

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.unsoed.responsi1mobileh1d023043.databinding.ActivityHeadCoachBinding
import com.unsoed.responsi1mobileh1d023043.model.Coach
import com.unsoed.responsi1mobileh1d023043.repository.PlayerRepository
import kotlinx.coroutines.launch

class HeadCoachActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHeadCoachBinding
    private lateinit var playerRepository: PlayerRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        Log.d("HeadCoachActivity", "onCreate called")
        
        binding = ActivityHeadCoachBinding.inflate(layoutInflater)
        setContentView(binding.root)

        playerRepository = PlayerRepository()
        
        setupInitialContent()
        setupClickListeners()
        loadCoachFromApi()
    }

    private fun setupInitialContent() {
        binding.tvCoachTitle.text = "Head Coach"
        // Show loading state
        binding.tvCoachName.text = "Loading..."
        binding.tvCoachPosition.text = "Loading..."
        binding.tvCoachNationality.text = "Loading..."
        binding.tvCoachAge.text = "Loading..."
        binding.tvCoachExperience.text = "Loading..."
        binding.tvCoachBiography.text = "Loading coach information from API..."
        binding.tvCareerHighlights.text = "Loading..."
        binding.tvCoachingPhilosophy.text = "Loading..."
    }

    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener {
            Log.d("HeadCoachActivity", "Back button clicked")
            finish()
        }
    }

    private fun loadCoachFromApi() {
        lifecycleScope.launch {
            try {
                Log.d("HeadCoachActivity", "🔄 Fetching coach data from API...")
                
                val result = playerRepository.getCoach()
                
                result.fold(
                    onSuccess = { coach ->
                        Log.d("HeadCoachActivity", "✅ API Success: Coach data loaded")
                        displayCoachData(coach)
                        
                        Toast.makeText(
                            this@HeadCoachActivity,
                            "✅ Coach data loaded from API",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    onFailure = { error ->
                        Log.e("HeadCoachActivity", "❌ API Error: ${error.message}")
                        displayNoDataMessage()
                        
                        Toast.makeText(
                            this@HeadCoachActivity,
                            "❌ No coach data available from API",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                )
                
            } catch (e: Exception) {
                Log.e("HeadCoachActivity", "❌ Exception: ${e.message}", e)
                displayErrorMessage()
                
                Toast.makeText(
                    this@HeadCoachActivity,
                    "❌ Error loading coach data",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    
    private fun displayCoachData(coach: Coach) {
        Log.d("HeadCoachActivity", "Displaying coach: ${coach.name}")
        
        // Basic info from API
        binding.tvCoachName.text = coach.name
        binding.tvCoachPosition.text = coach.position
        binding.tvCoachNationality.text = coach.nationality
        
        // Calculate age if birth date is available
        val age = if (coach.dateOfBirth != "Unknown" && coach.dateOfBirth.isNotEmpty()) {
            calculateAge(coach.dateOfBirth)
        } else {
            "Not available"
        }
        binding.tvCoachAge.text = age
        
        // Simple experience info
        binding.tvCoachExperience.text = "Professional Coach"
        
        // Basic biography based on API data
        binding.tvCoachBiography.text = """
            Name: ${coach.name}
            Nationality: ${coach.nationality}
            Position: ${coach.position}
            
            ${coach.name} is the current head coach of Levante UD. This information is retrieved directly from the official football database.
        """.trimIndent()
        
        // Career info
        binding.tvCareerHighlights.text = """
            🏆 Current Role:
            • Head Coach at Levante UD
            • Leading the team's tactical and strategic development
            
            📊 Data Source:
            • Information from official football database
            • Real-time coaching assignment data
        """.trimIndent()
        
        // Philosophy
        binding.tvCoachingPhilosophy.text = """
            💭 Coaching Information:
            
            ${coach.name} serves as the head coach for Levante UD, responsible for:
            
            • Team tactical setup and match preparation
            • Player development and team management
            • Strategic planning for competitive matches
            • Leading training sessions and team coordination
            
            Note: Detailed coaching philosophy and specific methodologies are not available in the current API data.
        """.trimIndent()
    }
    
    private fun displayNoDataMessage() {
        binding.tvCoachName.text = "Data Not Available"
        binding.tvCoachPosition.text = "No information"
        binding.tvCoachNationality.text = "Unknown"
        binding.tvCoachAge.text = "Unknown"
        binding.tvCoachExperience.text = "Unknown"
        
        binding.tvCoachBiography.text = """
            ❌ Coach Information Not Available
            
            The API does not currently provide coach information for Levante UD. This could be due to:
            
            • Coach data not included in the current API response
            • Temporary coaching arrangement
            • Data update in progress
            
            Please check back later for updated information.
        """.trimIndent()
        
        binding.tvCareerHighlights.text = "No career data available from API"
        binding.tvCoachingPhilosophy.text = "No coaching philosophy data available from API"
    }
    
    private fun displayErrorMessage() {
        binding.tvCoachName.text = "Error Loading Data"
        binding.tvCoachPosition.text = "Connection Error"
        binding.tvCoachNationality.text = "Unknown"
        binding.tvCoachAge.text = "Unknown"
        binding.tvCoachExperience.text = "Unknown"
        
        binding.tvCoachBiography.text = """
            ⚠️ Connection Error
            
            Unable to fetch coach information due to network or server issues.
            
            Please check your internet connection and try again.
        """.trimIndent()
        
        binding.tvCareerHighlights.text = "Unable to load career information"
        binding.tvCoachingPhilosophy.text = "Unable to load coaching information"
    }
    
    private fun calculateAge(birthDate: String): String {
        return try {
            // Parse birth date (format: YYYY-MM-DD)
            val parts = birthDate.split("-")
            if (parts.size >= 1) {
                val birthYear = parts[0].toInt()
                val currentYear = 2024
                val age = currentYear - birthYear
                "$age years old"
            } else {
                "Age not available"
            }
        } catch (e: Exception) {
            Log.e("HeadCoachActivity", "Error calculating age: ${e.message}")
            "Age not available"
        }
    }
}