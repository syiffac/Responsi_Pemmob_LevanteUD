package com.unsoed.responsi1mobileh1d023043

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.unsoed.responsi1mobileh1d023043.databinding.ItemPlayerBinding

class PlayerAdapter(
    private val onPlayerClick: (Player) -> Unit
) : RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>() {

    private var players = listOf<Player>()

    fun updatePlayers(newPlayers: List<Player>) {
        players = newPlayers
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val binding = ItemPlayerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PlayerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bind(players[position])
    }

    override fun getItemCount(): Int = players.size

    inner class PlayerViewHolder(
        private val binding: ItemPlayerBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(player: Player) {
            binding.tvPlayerName.text = player.name
            binding.tvPlayerCountry.text = player.country
            
            // Set background color berdasarkan posisi
            val backgroundColor = ContextCompat.getColor(
                binding.root.context,
                player.getPositionColor()
            )
            binding.llCardBackground.setBackgroundColor(backgroundColor)
            
            // Set click listener
            binding.root.setOnClickListener {
                onPlayerClick(player)
            }
        }
    }
}