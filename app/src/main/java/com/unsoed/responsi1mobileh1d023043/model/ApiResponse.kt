package com.unsoed.responsi1mobileh1d023043.model

import com.google.gson.annotations.SerializedName
import com.unsoed.responsi1mobileh1d023043.Player

// Response utama dari API
data class ApiResponse(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("name")
    val name: String,
    
    @SerializedName("shortName")
    val shortName: String,
    
    @SerializedName("coach")
    val coach: CoachApi?,
    
    @SerializedName("squad")
    val squad: List<PlayerApi>
)

// Model Coach dari API (Updated)
data class CoachApi(
    @SerializedName("id")
    val id: Int?,
    
    @SerializedName("name")
    val name: String,
    
    @SerializedName("dateOfBirth")
    val dateOfBirth: String?,
    
    @SerializedName("nationality")
    val nationality: String
) {
    fun toCoach(): Coach {
        return Coach(
            id = id?.toString() ?: "unknown",
            name = name,
            dateOfBirth = dateOfBirth ?: "Unknown",
            nationality = nationality,
            position = "Head Coach"
        )
    }
}

// Local Coach model
data class Coach(
    val id: String,
    val name: String,
    val dateOfBirth: String,
    val nationality: String,
    val position: String
)

// Model Player dari API (tetap sama)
data class PlayerApi(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("name")
    val name: String,
    
    @SerializedName("position")
    val position: String,
    
    @SerializedName("dateOfBirth")
    val dateOfBirth: String?,
    
    @SerializedName("nationality")
    val nationality: String,
    
    @SerializedName("shirtNumber")
    val shirtNumber: Int?
) {
    fun toPlayer(): Player {
        return Player(
            id = id.toString(),
            name = name,
            birthDate = dateOfBirth ?: "Unknown",
            country = nationality,
            position = mapPosition(position)
        )
    }
    
    private fun mapPosition(apiPosition: String): String {
        return when {
            apiPosition.equals("Goalkeeper", ignoreCase = true) -> "Goalkeeper"
            apiPosition.contains("Defender", ignoreCase = true) || 
            apiPosition.contains("Defence", ignoreCase = true) -> "Defender"
            apiPosition.contains("Midfielder", ignoreCase = true) || 
            apiPosition.contains("Midfield", ignoreCase = true) -> "Midfielder"
            apiPosition.contains("Forward", ignoreCase = true) || 
            apiPosition.contains("Attacker", ignoreCase = true) ||
            apiPosition.contains("Winger", ignoreCase = true) -> "Forward"
            else -> "Midfielder"
        }
    }
}