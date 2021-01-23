package com.example.goalcounter

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.drawToBitmap
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.goalcounter.databinding.ActivityTeamsBinding

class TeamsActivity: AppCompatActivity() {
    private val TAG = "TEAMS_ACTIVITY"
    private val binding: ActivityTeamsBinding by lazy {
        ActivityTeamsBinding.inflate(layoutInflater)
    }

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
        binding.tieWinner.error = "Min length - 3"
        binding.tieLoser.error = "Min length - 3"
        enableButtonStart(binding.tieWinner.error == null && binding.tieLoser.error == null)

        val twMinLength = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                s?.also {
                    if (it.length < 3) {
                        if (binding.tieWinner.isFocused) {
                            binding.tieWinner.error = "Min length - 3"
                        }
                        else {
                            binding.tieLoser.error = "Min length - 3"
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
        val imageList = arrayListOf<Int>()
        imageList.add(R.drawable.ic_basketball)
        imageList.add(R.drawable.ic_basketball)
        imageList.add(R.drawable.ic_basketball)
        imageList.add(R.drawable.ic_basketball)
        imageList.add(R.drawable.ic_basketball)
        imageList.add(R.drawable.ic_basketball)
        imageList.add(R.drawable.ic_basketball)
        imageList.add(R.drawable.ic_basketball)
        imageList.add(R.drawable.ic_basketball)
        imageList.add(R.drawable.ic_basketball)
        imageList.add(R.drawable.ic_basketball)


        binding.rvGameList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvGameList.adapter = GamesAdapter(imageList)
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

    private fun toMatchActivity() {}

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