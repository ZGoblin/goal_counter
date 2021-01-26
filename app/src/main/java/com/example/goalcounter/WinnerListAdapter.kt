package com.example.goalcounter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.goalcounter.databinding.WinnerListViewBinding

class WinnerListAdapter(private val winnerList: ArrayList<Winner>): RecyclerView.Adapter<WinnerHolder>() {
    override fun getItemCount(): Int {
        return winnerList.size
    }

    override fun onBindViewHolder(holder: WinnerHolder, position: Int) {
        holder.onBind(winnerList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WinnerHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.winner_list_view, parent, false)
        return WinnerHolder(view)
    }
}

class WinnerHolder(view: View): RecyclerView.ViewHolder(view) {
    private lateinit var binding: WinnerListViewBinding

    fun onBind(winner: Winner) {
        binding = WinnerListViewBinding.bind(itemView)

        binding.tvWinner.text = winner.name
        binding.tvScore.text = winner.score.toString()
    }
}