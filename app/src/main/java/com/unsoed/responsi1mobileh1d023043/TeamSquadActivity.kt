package com.unsoed.responsi1mobileh1d023043

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.unsoed.responsi1mobileh1d023043.databinding.ActivityTeamSquadBinding
import com.unsoed.responsi1mobileh1d023043.repository.PlayerRepository
import kotlinx.coroutines.launch

class TeamSquadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTeamSquadBinding
    private lateinit var playerAdapter: PlayerAdapter
    private lateinit var playerRepository: PlayerRepository
    private var allPlayers = mutableListOf<Player>()
    private var currentPosition = "All"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeamSquadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        playerRepository = PlayerRepository()
        
        setupRecyclerView()
        setupFilterButtons()
        loadPlayersFromApi()
    }

    private fun setupRecyclerView() {
        playerAdapter = PlayerAdapter { player ->
            showPlayerDetail(player)
        }
        binding.rvPlayers.apply {
            layoutManager = LinearLayoutManager(this@TeamSquadActivity)
            adapter = playerAdapter
        }
    }

    private fun setupFilterButtons() {
        // Reset semua button ke state normal
        resetButtonStates()
        
        // Set default button (All/Goalkeeper) sebagai selected
        binding.btnGoalkeeper.isSelected = true
        
        binding.btnGoalkeeper.setOnClickListener {
            filterByPosition("Goalkeeper")
            updateButtonStates(it)
        }

        binding.btnDefender.setOnClickListener {
            filterByPosition("Defender")
            updateButtonStates(it)
        }

        binding.btnMidfielder.setOnClickListener {
            filterByPosition("Midfielder")
            updateButtonStates(it)
        }

        binding.btnForward.setOnClickListener {
            filterByPosition("Forward")
            updateButtonStates(it)
        }
    }

    private fun updateButtonStates(selectedButton: View) {
        // Reset semua button
        resetButtonStates()
        
        // Set button yang dipilih sebagai selected
        selectedButton.isSelected = true
        selectedButton.alpha = 1.0f
    }

    private fun resetButtonStates() {
        listOf(
            binding.btnGoalkeeper,
            binding.btnDefender,
            binding.btnMidfielder,
            binding.btnForward
        ).forEach { button ->
            button.isSelected = false
            button.alpha = 0.7f
        }
    }

    private fun filterByPosition(position: String) {
        currentPosition = position
        val filteredPlayers = if (position == "All") {
            allPlayers
        } else {
            allPlayers.filter { it.position == position }
        }
        playerAdapter.updatePlayers(filteredPlayers)
        
        Log.d("TeamSquadActivity", "Filtered $position: ${filteredPlayers.size} players")
    }

    private fun loadPlayersFromApi() {
        showLoading(true)
        
        lifecycleScope.launch {
            try {
                Log.d("TeamSquadActivity", "Starting API call for Levante squad...")
                
                val result = playerRepository.getPlayers()
                
                result.fold(
                    onSuccess = { players ->
                        Log.d("TeamSquadActivity", "✅ API Success: ${players.size} players loaded from Levante")
                        
                        allPlayers.clear()
                        allPlayers.addAll(players)
                        
                        // Show breakdown by position
                        val goalkeepers = players.filter { it.position == "Goalkeeper" }
                        val defenders = players.filter { it.position == "Defender" }
                        val midfielders = players.filter { it.position == "Midfielder" }
                        val forwards = players.filter { it.position == "Forward" }
                        
                        Log.d("TeamSquadActivity", "Squad breakdown - GK: ${goalkeepers.size}, DEF: ${defenders.size}, MID: ${midfielders.size}, FWD: ${forwards.size}")
                        
                        filterByPosition("Goalkeeper")
                        showLoading(false)
                        
                        Toast.makeText(
                            this@TeamSquadActivity, 
                            "✅ Levante squad loaded from API", 
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    onFailure = { error ->
                        Log.e("TeamSquadActivity", "❌ API Error: ${error.message}")
                        loadDummyData()
                        showLoading(false)
                        
                        Toast.makeText(
                            this@TeamSquadActivity, 
                            "⚠️ Using offline data", 
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
                
            } catch (e: Exception) {
                Log.e("TeamSquadActivity", "❌ Exception: ${e.message}", e)
                loadDummyData()
                showLoading(false)
                
                Toast.makeText(
                    this@TeamSquadActivity, 
                    "❌ Error loading data", 
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    
    private fun loadDummyData() {
        Log.d("TeamSquadActivity", "Loading dummy Levante data...")
        allPlayers.clear()
        allPlayers.addAll(playerRepository.getDummyPlayers())
        filterByPosition("Goalkeeper")
    }
    
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            Log.d("TeamSquadActivity", "🔄 Loading Levante players...")
            // Bisa tambahkan loading indicator UI di sini
        } else {
            Log.d("TeamSquadActivity", "✅ Loading complete")
        }
    }

    private fun showPlayerDetail(player: Player) {
        val bottomSheet = PlayerDetailBottomSheet.newInstance(
            playerName = player.name,
            birthDate = player.birthDate,
            country = player.country,
            position = player.position
        )
        bottomSheet.show(supportFragmentManager, "PlayerDetail")
    }
}

// Data class untuk Player
data class Player(
    val id: String,
    val name: String,
    val birthDate: String,
    val country: String,
    val position: String
) {
    fun getPositionColor(): Int {
        return when (position) {
            "Goalkeeper" -> R.color.goalkeeper_color
            "Defender" -> R.color.defender_color
            "Midfielder" -> R.color.midfielder_color
            "Forward" -> R.color.forward_color
            else -> R.color.text_gray
        }
    }
}