package com.example.zockettask.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zockettask.MyApplication
import com.example.zockettask.data.db.entities.FacebookUser
import com.example.zockettask.data.network.FacebookLoginModel
import com.example.zockettask.data.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import retrofit2.Response

class FacebookLoginViewModel : ViewModel(), KodeinAware {

    override val kodein by kodein(MyApplication.appContext!!)

    private val userRepo: UserRepository by instance()

    var readAllData: LiveData<FacebookUser> = userRepo.getUser()

    fun getUserById(userId: String): LiveData<FacebookUser> {
        return userRepo.getUserById(userId)
    }

    fun addUser(user: FacebookUser) {

        viewModelScope.launch(Dispatchers.IO) {
            userRepo.addUser(user)
        }


    }

}