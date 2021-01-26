package com.example.goalcounter

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.provider.ContactsContract
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.drawToBitmap
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.goalcounter.databinding.ActivityTeamsBinding

class TeamsActivity: AppCompatActivity() {
    private val TAG = "TEAMS_ACTIVITY"
    private lateinit var adapter: GamesAdapter
    private val binding: ActivityTeamsBinding by lazy {
        ActivityTeamsBinding.inflate(layoutInflater)
    }
    private val gameList: ArrayList<Int> = arrayListOf()

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, TeamsActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupListener()
        setupRecyclerView()
        setupTextWatchers()
    }

    private fun setupTextWatchers() {
        binding.tieWinner.error = getString(R.string.min_length)
        binding.tieLoser.error = getString(R.string.min_length)
        enableButtonStart(binding.tieWinner.error == null && binding.tieLoser.error == null)

        val twMinLength = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                s?.also {
                    if (it.length < 3) {
                        if (binding.tieWinner.isFocused) {
                            binding.tieWinner.error = getString(R.string.min_length)
                        }
                        else {
                            binding.tieLoser.error = getString(R.string.min_length)
                        }
                    }
                    else {
                        if (binding.tieWinner.isFocused) {
                            binding.tieWinner.error = null
                        }
                        else {
                            binding.tieLoser.error = null
                        }
                    }
                }
                enableButtonStart(binding.tieWinner.error == null && binding.tieLoser.error == null)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        binding.tieWinner.addTextChangedListener(twMinLength)
        binding.tieLoser.addTextChangedListener(twMinLength)
    }

    private fun setupRecyclerView() {
        gameList.add(R.drawable.ic_football)
        gameList.add(R.drawable.ic_basketball)
        gameList.add(R.drawable.ic_hockey)
        gameList.add(R.drawable.ic_handball)
        gameList.add(R.drawable.ic_water_polo)
        gameList.add(R.drawable.ic_polo)
        gameList.add(R.drawable.ic_custom)

        adapter = GamesAdapter(gameList)

        binding.rvGameList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvGameList.adapter = adapter
    }

    private fun setupListener() {
        binding.btnBack.setOnClickListener {
            backToPreviousActivity()
        }
        binding.btnStart.setOnClickListener {
            toMatchActivity()
        }
    }

    private fun backToPreviousActivity() {
        finish()
    }

    private fun toMatchActivity() {
        val firstTeamName = binding.tieWinner.text.toString()
        val secondTeamName = binding.tieLoser.text.toString()
        val choosedGame = gameList[adapter.newPosition]
        if (R.drawable.ic_custom == choosedGame)
        {
            val dialogFragment = DialogTimesPicker(firstTeamName, secondTeamName)
            supportFragmentManager.beginTransaction()
                    .add(dialogFragment, "TAG")
                    .commitAllowingStateLoss()
        }
        else {
            val dataHolder = DataHolder.instance()
            val halfs = dataHolder.halfsInGame(choosedGame)
            val duration = dataHolder.durationsInGame(choosedGame)
            MatchActivity.start(this, firstTeamName, secondTeamName, halfs, duration)
        }
    }

    private fun enableButtonStart(enable: Boolean) {
        binding.btnStart.isEnabled = enable
        if (enable) {
            binding.btnStart.setBackgroundResource(R.drawable.button_style)
        }
        else {
            binding.btnStart.setBackgroundResource(R.drawable.button_style_disabled)
        }
    }
}