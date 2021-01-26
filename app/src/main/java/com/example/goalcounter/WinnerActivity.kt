package com.example.goalcounter

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.Image
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.drawToBitmap
import com.example.goalcounter.databinding.ActivityWinnerScreenBinding
import java.io.ByteArrayOutputStream

class WinnerActivity:AppCompatActivity() {
    private val binding: ActivityWinnerScreenBinding by lazy {
        ActivityWinnerScreenBinding.inflate(layoutInflater)
    }

    companion object {
        private const val FIRST_TEAM_NAME = "FIRST_TEAM_NAME"
        private const val SECOND_TEAM_NAME = "SECOND_TEAM_NAME"
        private const val FIRST_TEAM_SCORE = "FIRST_TEAM_SCORE"
        private const val SECOND_TEAM_SCORE = "SECOND_TEAM_SCORE"

        fun start(context: Context, firstTeam: String, secondTeam: String, firstTeamScore: Int, secondTeamScore: Int) {
            val intent = Intent(context, WinnerActivity::class.java)
            intent.putExtra(FIRST_TEAM_NAME, firstTeam)
            intent.putExtra(SECOND_TEAM_NAME, secondTeam)
            intent.putExtra(FIRST_TEAM_SCORE, firstTeamScore)
            intent.putExtra(SECOND_TEAM_SCORE, secondTeamScore)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupData()
        setupListener()
    }

    private fun setupListener() {
        binding.btnShare.setOnClickListener {
            shareResult()
        }
        binding.btnMenu.setOnClickListener {
            toMainMenu()
        }
        binding.btnWinnerList.setOnClickListener {
            toListActivity()
        }
    }

    private fun toListActivity() {
        ListActivity.start(this)
    }

    private fun toMainMenu() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun shareResult() {
        if (ContextCompat.checkSelfPermission(this@WinnerActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            val photoIntent = Intent()
            val photo = getScreenshot()
            val photoPath = MediaStore.Images.Media.insertImage(contentResolver, photo, "Winner", null)
            val uri = Uri.parse(photoPath)
            photoIntent.putExtra(Intent.EXTRA_STREAM, uri)
            photoIntent.type = "image/*"
            photoIntent.action = Intent.ACTION_SEND
            startActivity(Intent.createChooser(photoIntent, "Winner screen"))
        }
        else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this@WinnerActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this@WinnerActivity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
            } else {
                ActivityCompat.requestPermissions(this@WinnerActivity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
            }
        }
    }

    override fun onBackPressed() {
        toMainMenu()
    }

    private fun getScreenshot(): Bitmap {
        binding.btnMenu.visibility = View.GONE
        binding.btnShare.visibility = View.GONE
        binding.btnWinnerList.visibility = View.GONE
        val screenshot = binding.clMain.drawToBitmap()
        binding.btnMenu.visibility = View.VISIBLE
        binding.btnShare.visibility = View.VISIBLE
        binding.btnWinnerList.visibility = View.VISIBLE
        return screenshot
    }

    private fun setupData() {
        val firstTeamScore = intent.getIntExtra(FIRST_TEAM_SCORE, 0)
        val secondTeamScore = intent.getIntExtra(SECOND_TEAM_SCORE, 0)
        binding.tvScore.text = getString(R.string.score, firstTeamScore, secondTeamScore)
        if (secondTeamScore > firstTeamScore) {
            val winner = intent.getStringExtra(SECOND_TEAM_NAME)
            binding.tvWinnerName.text = winner
            binding.ivWinner.setImageResource(R.drawable.ic_playerl)
            binding.tvLoserName.text = intent.getStringExtra(FIRST_TEAM_NAME)
            binding.ivLoser.setImageResource(R.drawable.ic_playerw)
            winner?.let {
                DataHolder.instance().addWinner(it, secondTeamScore)
            }
        }
        else {
            val winner = intent.getStringExtra(FIRST_TEAM_NAME)
            binding.tvWinnerName.text = intent.getStringExtra(FIRST_TEAM_NAME)
            binding.ivWinner.setImageResource(R.drawable.ic_playerw)
            binding.tvLoserName.text = intent.getStringExtra(SECOND_TEAM_NAME)
            binding.ivLoser.setImageResource(R.drawable.ic_playerl)
            if (secondTeamScore != firstTeamScore) {
                winner?.let {
                    DataHolder.instance().addWinner(it, firstTeamScore)
                }
            }
        }
    }

}