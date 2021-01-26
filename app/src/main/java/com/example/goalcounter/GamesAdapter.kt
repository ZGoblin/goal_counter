package com.example.goalcounter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.goalcounter.databinding.RecyclerItemViewBinding

class GamesAdapter(private val gameImages: ArrayList<Int>) : RecyclerView.Adapter<GameHolder>() {
     var newPosition: Int = 0
    private var oldPosition: Int = -1

    private fun setSelectedPosition(position: Int) {
        oldPosition = newPosition
        newPosition = position
        notifyItemChanged(newPosition)
        notifyItemChanged(oldPosition)
    }

    override fun getItemCount(): Int {
        return gameImages.size
    }

    override fun onBindViewHolder(holder: GameHolder, position: Int) {
        holder.bind(gameImages[position], position == newPosition)
        holder.itemView.setOnClickListener {
            setSelectedPosition(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_item_view, parent, false)
        return GameHolder(view)
    }
}

class GameHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding: RecyclerItemViewBinding by lazy {
        RecyclerItemViewBinding.bind(view)
    }

    fun bind(image: Int, isSelected: Boolean) {
        binding.ivImage.setImageResource(image)

        if (isSelected) {
            binding.ivImage.setBackgroundResource(R.drawable.button_style)
        }
        else {
            binding.ivImage.background = null
        }
    }
}