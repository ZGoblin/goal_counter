package com.example.goalcounter

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import com.example.goalcounter.databinding.DialogTimerSettingBinding
import java.lang.NumberFormatException

class DialogTimesPicker(private val firstTeamName: String, private val secondTeamName: String): DialogFragment() {
    private lateinit var binding: DialogTimerSettingBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogTimerSettingBinding.inflate(inflater, container, false)

        setupListener()
        setupTextWatchers()
        return binding.root
    }

    private fun setupTextWatchers() {
        binding.tieTimes.error = "From 1 to 20"
        binding.tieDuration.error = "From 1 to 90"
        binding.btnGo.isEnabled = binding.tieTimes.error == null && binding.tieDuration.error == null

        val twMinLength = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                s?.also {
                    var input: Int = 0
                    try {
                        input = it.toString().toInt()
                    }
                    catch (exception: NumberFormatException) {
                        return@also
                    }
                    if (binding.tieTimes.isFocused) {
                        if (input in 1..20) {
                            binding.tieTimes.error = null
                        }
                        else {
                            binding.tieTimes.error = "From 1 to 20"
                        }
                    }
                    else if (binding.tieDuration.isFocused) {
                        if (input in 1..91) {
                            binding.tieDuration.error = null
                        }
                        else {
                            binding.tieDuration.error = "From 1 to 90"
                        }
                    }
                }
                binding.btnGo.isEnabled = binding.tieTimes.error == null && binding.tieDuration.error == null
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        binding.tieTimes.addTextChangedListener(twMinLength)
        binding.tieDuration.addTextChangedListener(twMinLength)
    }

    private fun setupListener() {
        binding.btnGo.setOnClickListener {
            setToDataHolder()
        }
    }

    private fun setToDataHolder() {
        val halfs = binding.tieTimes.text.toString().toInt()
        val duration = binding.tieDuration.text.toString().toLong() * 60
        dismiss()
        context?.let { MatchActivity.start(it, firstTeamName, secondTeamName, halfs, duration) }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        dialog?.window?.setBackgroundDrawableResource(R.color.transparent_green)
        dialog?.setCancelable(false)
    }
}