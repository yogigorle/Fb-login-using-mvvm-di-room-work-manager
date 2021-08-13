package com.example.zockettask.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.zockettask.data.db.entities.FacebookUser

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(facebookUser: FacebookUser)

    @Query("SELECT * FROM user_table ORDER BY userId DESC LIMIT 1")
    fun getLastInsertedUser(): LiveData<FacebookUser>

    @Query("SELECT * FROM user_table ORDER BY userId DESC LIMIT 1")
    fun getLastInsertedUserForSync(): FacebookUser

    @Query("SELECT * FROM user_table WHERE userId == :userId")
    fun getUserById(userId: String): LiveData<FacebookUser>
}