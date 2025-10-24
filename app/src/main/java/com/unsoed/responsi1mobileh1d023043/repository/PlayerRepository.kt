package com.unsoed.responsi1mobileh1d023043.repository

import android.util.Log
import com.unsoed.responsi1mobileh1d023043.Player
import com.unsoed.responsi1mobileh1d023043.model.Coach
import com.unsoed.responsi1mobileh1d023043.network.RetrofitClient

class PlayerRepository {
    
    private val apiService = RetrofitClient.apiService
    
    // Function untuk get players dari API
    suspend fun getPlayers(): Result<List<Player>> {
        return try {
            Log.d("PlayerRepository", "Fetching Levante squad from API...")
            
            // Call API dengan ID Levante = 88
            val response = apiService.getTeamSquad(
                teamId = RetrofitClient.LEVANTE_TEAM_ID,
                token = RetrofitClient.API_TOKEN
            )
            
            if (response.isSuccessful) {
                val apiResponse = response.body()
                
                if (apiResponse != null) {
                    // Convert API players ke local Player model
                    val players = apiResponse.squad.map { playerApi ->
                        playerApi.toPlayer()
                    }
                    
                    Log.d("PlayerRepository", "Successfully fetched ${players.size} players from Levante squad")
                    Log.d("PlayerRepository", "Team: ${apiResponse.name}")
                    
                    Result.success(players)
                } else {
                    val errorMsg = "Response body is null"
                    Log.e("PlayerRepository", errorMsg)
                    Result.failure(Exception(errorMsg))
                }
                
            } else {
                val errorMessage = "API Error: ${response.code()} - ${response.message()}"
                Log.e("PlayerRepository", errorMessage)
                
                // Log response error body untuk debugging
                val errorBody = response.errorBody()?.string()
                Log.e("PlayerRepository", "Error body: $errorBody")
                
                Result.failure(Exception(errorMessage))
            }
            
        } catch (e: Exception) {
            Log.e("PlayerRepository", "Network/Parsing error: ${e.message}", e)
            Result.failure(e)
        }
    }
    
    // Function untuk get coach dari API (Updated)
    suspend fun getCoach(): Result<Coach> {
        return try {
            Log.d("PlayerRepository", "🔄 Fetching coach data from API...")
            
            val response = apiService.getTeamSquad(
                teamId = RetrofitClient.LEVANTE_TEAM_ID,
                token = RetrofitClient.API_TOKEN
            )
            
            if (response.isSuccessful) {
                val apiResponse = response.body()
                
                if (apiResponse != null) {
                    Log.d("PlayerRepository", "📊 API Response received for team: ${apiResponse.name}")
                    
                    if (apiResponse.coach != null) {
                        val coach = apiResponse.coach.toCoach()
                        Log.d("PlayerRepository", "✅ Coach found: ${coach.name} (${coach.nationality})")
                        Result.success(coach)
                    } else {
                        Log.w("PlayerRepository", "⚠️ No coach data in API response")
                        Result.failure(Exception("No coach data available in API response"))
                    }
                } else {
                    Log.e("PlayerRepository", "❌ API response body is null")
                    Result.failure(Exception("Empty response from API"))
                }
                
            } else {
                val errorMessage = "API Error: ${response.code()} - ${response.message()}"
                val errorBody = response.errorBody()?.string()
                Log.e("PlayerRepository", "$errorMessage | Error body: $errorBody")
                Result.failure(Exception(errorMessage))
            }
            
        } catch (e: Exception) {
            Log.e("PlayerRepository", "❌ Network/Parsing error: ${e.message}", e)
            Result.failure(e)
        }
    }
    
    // Dummy data sebagai fallback
    fun getDummyPlayers(): List<Player> {
        Log.d("PlayerRepository", "Using dummy data for Levante UD squad")
        
        return listOf(
            // Goalkeepers
            Player("1", "Daniel Cárdenas", "1996-10-08", "Spain", "Goalkeeper"),
            Player("13", "Aitor Fernández", "1991-04-24", "Spain", "Goalkeeper"),
            
            // Defenders
            Player("2", "Son", "1992-06-22", "South Korea", "Defender"),
            Player("3", "Carlos Clerc", "1993-01-15", "Spain", "Defender"),
            Player("14", "Rúben Vezo", "1994-04-25", "Portugal", "Defender"),
            
            // Midfielders
            Player("5", "Gonzalo Melero", "1993-02-17", "Spain", "Midfielder"),
            Player("6", "Óliver Torres", "1994-11-10", "Spain", "Midfielder"),
            Player("8", "Pepelu", "1998-08-11", "Spain", "Midfielder"),
            
            // Forwards
            Player("7", "José Luis Morales", "1987-07-22", "Spain", "Forward"),
            Player("9", "Roger Martí", "1991-03-05", "Spain", "Forward"),
            Player("11", "Dani Gómez", "1991-08-12", "Spain", "Forward")
        )
    }
    
    // Function untuk get players berdasarkan posisi
    fun getPlayersByPosition(players: List<Player>, position: String): List<Player> {
        return if (position == "All") {
            players
        } else {
            players.filter { it.position == position }
        }
    }
}