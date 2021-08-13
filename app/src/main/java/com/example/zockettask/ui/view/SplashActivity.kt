package com.example.zockettask.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.zockettask.ui.viewmodel.FacebookLoginViewModel
import com.example.zockettask.utils.Constants
import com.example.zockettask.utils.getString
import com.example.zockettask.utils.launchActivity
import com.example.zockettask.utils.viewModel

class SplashActivity : AppCompatActivity() {

    private val fbViewModel: FacebookLoginViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val storedUserId = getString(Constants.fb_user_id)!!

        fbViewModel.getUserById(storedUserId).observe(this, Observer {
            if (it?.let { it.userId == storedUserId } == true) {
                launchActivity<HomeActivity>()
            } else {
                launchActivity<FacebookLoginActivity>()
            }
        })
    }

}