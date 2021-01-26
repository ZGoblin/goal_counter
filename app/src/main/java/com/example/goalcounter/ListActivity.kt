package com.example.goalcounter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.goalcounter.databinding.ActivityListBinding

class ListActivity: AppCompatActivity() {
    private val binding: ActivityListBinding by lazy {
        ActivityListBinding.inflate(layoutInflater)
    }
    private lateinit var adapter: WinnerListAdapter

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, ListActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupListener()
        setupList()
    }

    private fun setupList() {
        adapter = WinnerListAdapter(DataHolder.instance().winnerList)

        binding.rvGameList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvGameList.adapter = adapter
    }

    private fun setupListener() {
        binding.btnClear.setOnClickListener {
            DataHolder.instance().winnerList.clear()
            adapter.notifyDataSetChanged()
        }
        binding.btnMenu.setOnClickListener {
            toMainMenu()
        }
    }

    private fun toMainMenu() {
        startActivity(Intent(this, MainActivity::class.java))
    }

}