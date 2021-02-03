package com.cryptenet.wormanager_test.works

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import timber.log.Timber

class ManualWork(appContext: Context, workerParams: WorkerParameters): Worker(appContext, workerParams) {
    override fun doWork(): Result {
        Timber.wtf("${ManualWork::class.java.simpleName}: Started working")

        return Result.success()
    }
}