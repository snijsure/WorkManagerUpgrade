package com.snijsure.workmanagerupgrade

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.*
import com.snijsure.workmanagerupgrade.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val cal: Calendar = Calendar.getInstance()
    private val timeFormat: SimpleDateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault());

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }

        binding.startWorkButton.setOnClickListener {
            binding.workManagerProgress.append("Work has been scheduled\n")
            WorkManager.getInstance(application).enqueueUniquePeriodicWork(
                WORKER_TAG,
                ExistingPeriodicWorkPolicy.REPLACE,
                getPeriodicWorkRequest()
            )
            binding.startWorkButton.setText(R.string.work_started)
            binding.startWorkButton.isClickable = false
            binding.startWorkButton.isEnabled = false

        }
        WorkManager.getInstance(application).getWorkInfosByTagLiveData(WORKER_TAG).observe(
            this,
            sampleWorkInfoObserver()
        )
    }

    private fun getConstraints(): Constraints {
        return Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
    }

    private fun getPeriodicWorkRequest(): PeriodicWorkRequest {
        return PeriodicWorkRequest.Builder(SampleWorker::class.java,
            INTERVAL_15_MIN,
            TimeUnit.MINUTES
        )
            .addTag(WORKER_TAG)
            .setConstraints(getConstraints())
            .build()
    }

    private fun sampleWorkInfoObserver(): Observer<List<WorkInfo>> {
        return Observer { listOfWorkInfo ->
            val currentTime: String = timeFormat.format(cal.time)
            if (listOfWorkInfo.isNullOrEmpty()) {
                binding.workManagerProgress.append("$currentTime -> No worker info\n")
                return@Observer
            }
            val workInfo = listOfWorkInfo[0]
            binding.workManagerProgress.append("$currentTime -> work status ${workInfo.state}\n")
        }
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}