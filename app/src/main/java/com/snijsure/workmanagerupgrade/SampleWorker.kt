package com.snijsure.workmanagerupgrade

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.preference.PreferenceManager
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Calendar

class SampleWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    private val timeFormat: SimpleDateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault());
    private val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    override fun doWork(): Result {
        val workExecTime: String = timeFormat.format(Calendar.getInstance().time)
        prefs.edit().putString(EXEC_TIME, workExecTime).apply()
        Log.d(TAG, "SampleWorker start task ${Thread.currentThread().name}")
        return Result.success()
    }

    companion object {
        val TAG: String = SampleWorker::class.java.simpleName
    }
}