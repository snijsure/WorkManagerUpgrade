package com.snijsure.workmanagerupgrade

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class SampleWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    override fun doWork(): Result {
        Log.d(TAG, "SampleWorker start task ${Thread.currentThread().name}")
        return Result.success()
    }

    companion object {
        val TAG: String = SampleWorker::class.java.simpleName
    }
}