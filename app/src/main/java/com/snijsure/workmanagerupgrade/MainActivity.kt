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
        binding.workManagerProgress.append("Worked has been removed no more classException\n")
    }
}