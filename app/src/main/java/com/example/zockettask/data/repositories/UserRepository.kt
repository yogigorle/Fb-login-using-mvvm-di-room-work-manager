package com.example.zockettask.data.repositories

import com.example.zockettask.data.db.UserDatabase
import com.example.zockettask.data.db.entities.FacebookUser
import com.example.zockettask.data.network.ApiInterface
import com.example.zockettask.data.network.FacebookLoginModel
import com.github.salomonbrys.kotson.jsonObject
import retrofit2.Response

class UserRepository(private val userDb: UserDatabase, private val apiService: ApiInterface) {

    private val userDao = userDb.userDao()

    fun getUser() = userDao.getLastInsertedUser()

    fun getUserForSync() = userDao.getLastInsertedUserForSync()

    fun getUserById(userId: String) = userDao.getUserById(userId)

    suspend fun uploadFacebookDetails(facebookUser: FacebookUser): Response<FacebookLoginModel> {
        return apiService.uploadFacebookDetails(
            jsonObject(
                "page_id" to facebookUser.userId,
                "name" to facebookUser.name,
                "access_token" to facebookUser.accessToken,
                "emails" to facebookUser.email,
                "picture" to facebookUser.profilePicPath
            )
        )
    }


    suspend fun addUser(facebookUser: FacebookUser) {
        userDao.addUser(facebookUser)
    }


}