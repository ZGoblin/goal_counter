package com.example.goalcounter

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
        binding.btnWinnerList.setOnClickListener {
            toListActivity()
        }
    }

    private fun toListActivity() {
        ListActivity.start(this)
    }

    private fun toTeamsActivity() {
        TeamsActivity.start(this)
    }
}