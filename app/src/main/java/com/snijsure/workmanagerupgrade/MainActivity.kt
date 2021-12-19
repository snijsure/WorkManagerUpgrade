package com.snijsure.workmanagerupgrade

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.snijsure.workmanagerupgrade.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
            simpleChronometer.start()
        }
        setupButton()
    }

    private fun setupButton() {
        binding.workManagerProgress.setText(
            R.string.worker_removed
        )
        binding.workManagerProgress.append("\n")
        binding.startWorkButton.isClickable = false
        binding.startWorkButton.isEnabled = false
    }
}