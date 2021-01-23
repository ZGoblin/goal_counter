package com.example.goalcounter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.goalcounter.databinding.RecyclerItemViewBinding

class GamesAdapter(private val gameImages: ArrayList<Int>) : RecyclerView.Adapter<GameHolder>() {

    override fun getItemCount(): Int {
        return gameImages.size
    }

    override fun onBindViewHolder(holder: GameHolder, position: Int) {
        holder.bind(gameImages[position])
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

    fun bind(image: Int) {
        binding.ivImage.setImageResource(image)

        binding.ivImage.setOnClickListener {
            it.setBackgroundColor(Color.MAGENTA)
        }
    }
}