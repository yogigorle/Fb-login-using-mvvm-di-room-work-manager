package com.example.zockettask.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.zockettask.data.repositories.UserRepository
import com.example.zockettask.utils.sendNotification
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import retrofit2.HttpException

class UploadDataWorker(val ctx: Context, params: WorkerParameters) :
    CoroutineWorker(ctx, params), KodeinAware {

    override val kodein by kodein(ctx)

    private val userRepo: UserRepository by instance()

    companion object {
        const val WORK_NAME = "com.example.zockettask.data.worker.UploadDataWorker"
    }


    override suspend fun doWork(): Result {

        try {

            val response = userRepo.getUserForSync()
            return if (response != null) {
                val result = userRepo.uploadFacebookDetails(response)
                result.let {
                    if (it.body()?.status.equals("success"))
                        sendNotification(
                            "Data has been successfully synced up",
                            ctx,
                            "Successfully Synced"
                        )
                }
                Result.success()
            } else {
                Result.retry()
            }

        } catch (e: HttpException) {
            return Result.retry()
        }
    }


}