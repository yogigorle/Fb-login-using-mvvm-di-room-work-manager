package com.example.zockettask.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.zockettask.utils.Constants


@Entity(tableName = Constants.databaseTableName)
data class FacebookUser(
    @PrimaryKey(autoGenerate = false)
    val userId: String,
    val email: String,
    val name: String,
    val profilePicPath: String,
    val accessToken: String
) {


}