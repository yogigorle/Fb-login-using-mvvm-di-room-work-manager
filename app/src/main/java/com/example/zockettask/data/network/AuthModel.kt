package com.example.zockettask.data.network

import com.google.gson.JsonObject

data class FacebookLoginModel(
    val status : String?,
    val result: List<JsonObject>
)