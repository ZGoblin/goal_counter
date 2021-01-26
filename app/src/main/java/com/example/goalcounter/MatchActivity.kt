package com.example.goalcounter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.example.goalcounter.databinding.ActivityMatchBinding
import java.lang.NumberFormatException
import java.lang.StringBuilder

class MatchActivity: AppCompatActivity() {
    private val binding: ActivityMatchBinding by lazy {
        ActivityMatchBinding.inflate(layoutInflater)
    }
    private var duration: Long = 60
    private var times: Int = 1
    private var curHalf: Int = 1
    private var firstTeamScore = 0
    private var secondTeamScore = 0

    companion object {
        const val FIRST_TEAM_NAME = "FIRST_TEAM_NAME"
        const val SECOND_TEAM_NAME = "SECOND_TEAM_NAME"
        const val TIMES = "TIMES"
        const val DURATION = "DURATION"

        const val FIRST_TEAM: Byte = 0
        const val SECOND_TEAM: Byte = 1

        fun start(context: Context, firstTeam: String, secondTeam: String, halfs: Int, duration: Long) {
            val intent = Intent(context, MatchActivity::class.java)
            intent.putExtra(FIRST_TEAM_NAME, firstTeam)
            intent.putExtra(SECOND_TEAM_NAME, secondTeam)
            intent.putExtra(TIMES, halfs)
            intent.putExtra(DURATION, duration)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupData()
        setupListener()
        setupTimer()
    }

    private fun setupData() {
        binding.tvFirstTeam.text = intent.getStringExtra(FIRST_TEAM_NAME)
        binding.tvSecondTeam.text = intent.getStringExtra(SECOND_TEAM_NAME)
        duration = intent.getLongExtra(DURATION, 60)
        times = intent.getIntExtra(TIMES, 1)
    }

    private fun setupTimer() {
        binding.tvPeriod.text = getString(R.string.period, curHalf)
        binding.tvTimer.text = getString(R.string.score, 0, 0)
        object: CountUpTimer(duration* MILLISECONDS_IN_SECOND) {
            override fun onTickListener(second: Long) {
                val minutes = second / 60
                val seconds = second % 60
                val timeText = StringBuilder()
                        .append("${if (minutes == 0L) "00" else if (minutes < 10L) "0$minutes" else minutes}")
                        .append(":")
                        .append("${if (seconds < 10L) "0$seconds" else seconds}")
                binding.tvTimer.text = timeText
            }

            override fun onFinish() {
                timerIsFinished()
            }
        }.start()
    }

    private fun timerIsFinished() {
        if (curHalf >= times) {
            toWinnerScreen()
        }
        else {
            ++curHalf
            MaterialDialog(this).show {
                title(R.string.time_is_over)
                message(R.string.time_is_over_message)
                positiveButton(R.string.play_button) {
                    setupTimer()
                }
            }
        }
    }

    private fun toWinnerScreen() {
        val firstTeamName = binding.tvFirstTeam.text.toString()
        val secondTeamName = binding.tvSecondTeam.text.toString()
        WinnerActivity.start(this, firstTeamName, secondTeamName, firstTeamScore, secondTeamScore)
    }

    private fun setupListener() {
        binding.btnCancel.setOnClickListener {
            toMainMenu()
        }
        binding.btnGoalFirst.setOnClickListener {
            incScore(FIRST_TEAM)
        }
        binding.btnGoalSecond.setOnClickListener {
            incScore(SECOND_TEAM)
        }
        binding.btnFinish.setOnClickListener {
            toWinnerScreen()
        }
    }

    private fun incScore(team: Byte) {
        if (team == FIRST_TEAM && firstTeamScore <= 999 && secondTeamScore <= 999) {
            firstTeamScore++
        }
        else {
            secondTeamScore++
        }

        val incrementedScore = StringBuilder()
                .append(firstTeamScore)
                .append(":")
                .append(secondTeamScore)
                .toString()

        when (incrementedScore.length) {
            4 -> binding.tvScore.setTextSize(TypedValue.COMPLEX_UNIT_SP, 76F)
            5 -> binding.tvScore.setTextSize(TypedValue.COMPLEX_UNIT_SP, 56F)
            in 6..8 -> binding.tvScore.setTextSize(TypedValue.COMPLEX_UNIT_SP, 36F)
        }
        binding.tvScore.text = incrementedScore
    }

    private fun toMainMenu() {
        MaterialDialog(this).show {
            title(R.string.exit)
            message(R.string.exit_message)
            positiveButton(R.string.yes_button) {
                startActivity(Intent(applicationContext, MainActivity::class.java))
            }
            negativeButton(R.string.no_button) {
                it.dismiss()
            }
        }
    }
}