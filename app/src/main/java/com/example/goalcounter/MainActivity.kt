package com.example.goalcounter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.goalcounter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupListener()
    }

    private fun setupListener() {
        binding.btnNewGame.setOnClickListener {
            toTeamsActivity()
        }
    }

    private fun toTeamsActivity() {
        TeamsActivity.start(this)
    }
}