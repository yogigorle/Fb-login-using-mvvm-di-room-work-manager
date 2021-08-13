package com.example.zockettask.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.work.*
import com.example.zockettask.R
import com.example.zockettask.data.worker.UploadDataWorker
import com.example.zockettask.databinding.ActivityHomeBinding
import com.example.zockettask.ui.viewmodel.FacebookLoginViewModel
import com.example.zockettask.utils.viewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit

class HomeActivity : AppCompatActivity() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)


    private val fbLoginViewModel: FacebookLoginViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val databinding =
            DataBindingUtil.setContentView<ActivityHomeBinding>(this, R.layout.activity_home)

        fbLoginViewModel.readAllData.observe(this, Observer {
            databinding.userModel = it
        })

        delayedInit()

    }

    private fun delayedInit() {
        applicationScope.launch {
            Timber.plant(Timber.DebugTree())
            setUpRecurringWork()

        }
    }

    private fun setUpRecurringWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val periodicWorkRequest = PeriodicWorkRequestBuilder<UploadDataWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        Timber.d("Work request started")

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            UploadDataWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWorkRequest
        )


    }
}