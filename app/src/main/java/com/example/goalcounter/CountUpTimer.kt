package com.example.goalcounter

import android.os.CountDownTimer

abstract class CountUpTimer(private val duration: Long): CountDownTimer(duration, MILLISECONDS_IN_SECOND) {

    abstract fun onTickListener(second: Long)
    abstract override fun onFinish()

    override fun onTick(millisUntilFinished: Long) {
        val second = (duration - millisUntilFinished) / MILLISECONDS_IN_SECOND
        onTickListener(second)
    }

    companion object {
        const val MILLISECONDS_IN_SECOND = 1000L
    }
}