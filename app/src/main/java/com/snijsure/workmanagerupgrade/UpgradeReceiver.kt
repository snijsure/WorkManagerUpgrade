package com.snijsure.workmanagerupgrade

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.WorkManager

class UpgradeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "UpgradeReceiver intent " + intent.action)
        if (intent.action == Intent.ACTION_MY_PACKAGE_REPLACED) {
            val workManager = WorkManager.getInstance(context)
            val removedWorkers = listOf("com.snijsure.workmanagerupgrade.SampleWorker")
            removedWorkers.forEach {
                try {
                    workManager.cancelAllWorkByTag(it)
                    Log.d(TAG, "Cancelled worker $it on upgrade")
                } catch (e: Throwable) {
                    Log.d(TAG, "Unable to cancel worker $it on upgrade WTF $e")

                }
            }
        }
    }

    companion object {
        private val TAG = UpgradeReceiver::class.java.simpleName
    }
}