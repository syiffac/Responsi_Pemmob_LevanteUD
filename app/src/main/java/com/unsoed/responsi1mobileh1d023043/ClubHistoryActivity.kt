package com.unsoed.responsi1mobileh1d023043

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.unsoed.responsi1mobileh1d023043.databinding.ActivityClubHistoryBinding

class ClubHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityClubHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClubHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupContent()
        setupClickListeners()
    }

    private fun setupContent() {
        binding.tvHistoryTitle.text = "Club History"
        binding.tvHistoryContent.text = getClubHistoryContent()
    }

    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun getClubHistoryContent(): String {
        return """
            Levante Unión Deportiva, commonly known as Levante UD or simply Levante, is a Spanish football club based in Valencia, in the Valencian Community.
            
            📅 Founded: 9 September 1909
            
            The club was founded by a group of Valencian students who had returned from studies in Switzerland, where they had been introduced to football.
            
            🏟️ Stadium: Estadi Ciutat de València
            Capacity: 26,354 spectators
            
            🏆 Major Achievements:
            • Copa del Rey Runner-up (1935-36)
            • Segunda División Champions (2003-04)
            • UEFA Europa League participant (2012-13, 2013-14)
            • Multiple La Liga campaigns
            
            📈 Historic Milestones:
            • 1909: Club foundation
            • 1963: First promotion to La Liga
            • 2004: Segunda División title
            • 2010s: Golden era with consistent La Liga presence
            • European competitions participation
            
            🎨 Club Identity:
            The club's colors are blue and red, and they are known as "Los Granotas" (The Frogs) due to their location near the wetlands of Valencia.
            
            🏘️ Community Connection:
            Levante has been a symbol of perseverance and community spirit in Valencian football, representing the working-class neighborhoods of Valencia with pride and determination.
            
            The club continues to be an important part of Valencia's football landscape, competing with passion and representing their community values.
        """.trimIndent()
    }
}