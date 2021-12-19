package com.snijsure.workmanagerupgrade

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import androidx.work.*
import com.snijsure.workmanagerupgrade.databinding.ActivityMainBinding
import java.util.*
import java.util.concurrent.TimeUnit

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

    private fun workStatusUpdate(
        workAlreadyScheduled: Boolean,
        id: UUID,
    ) {
        binding.workManagerProgress.setText(
            if (workAlreadyScheduled)
                R.string.work_already_started
            else
                R.string.new_work_started
        )
        binding.workManagerProgress.append("\n")
        binding.startWorkButton.isClickable = false
        binding.startWorkButton.isEnabled = false
        WorkManager.getInstance(application).getWorkInfoByIdLiveData(id)
            .observe(this, { workStatus ->
                if (workStatus.state == WorkInfo.State.ENQUEUED) {
                    val prefs: SharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(application)
                    val workExecTime = prefs.getString(EXEC_TIME, "Unknown")
                    if (!workExecTime.isNullOrBlank()) {
                        binding.workManagerProgress.append("$workExecTime -> work completed\n")
                    } else {
                        binding.workManagerProgress.append("No exec time\n")
                    }
                }
            })
    }

    private fun setupButton() {
        val workInfoList: MutableList<WorkInfo>? =
            WorkManager.getInstance(application).getWorkInfosByTag(WORKER_TAG).get()
        if (!workInfoList.isNullOrEmpty()) {
            workStatusUpdate(
                workAlreadyScheduled = true,
                id = workInfoList[0].id,
            )
        } else {
            val workRequest = getPeriodicWorkRequest()
            binding.startWorkButton.setOnClickListener {
                WorkManager.getInstance(application).enqueueUniquePeriodicWork(
                    WORKER_TAG,
                    ExistingPeriodicWorkPolicy.REPLACE,
                    workRequest
                )
                workStatusUpdate(
                    workAlreadyScheduled = false,
                    id = workRequest.id,
                )
            }
        }
    }

    private fun getConstraints(): Constraints {
        return Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
    }

    private fun getPeriodicWorkRequest(): PeriodicWorkRequest {
        return PeriodicWorkRequest.Builder(
            SampleWorker::class.java,
            INTERVAL_15_MIN,
            TimeUnit.MINUTES
        )
            .addTag(WORKER_TAG)
            .setConstraints(getConstraints())
            .build()
    }
}