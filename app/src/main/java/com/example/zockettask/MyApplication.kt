package com.example.zockettask

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.work.*
import com.example.zockettask.data.db.UserDatabase
import com.example.zockettask.data.network.ApiInterface
import com.example.zockettask.data.repositories.UserRepository
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class MyApplication : Application(), KodeinAware {


    override val kodein = Kodein.lazy {
        import(androidXModule(this@MyApplication))

        bind() from singleton { UserDatabase.getDatabase(instance()) }
        bind() from singleton { ApiInterface.create() }
        bind() from singleton { UserRepository(instance(), instance()) }


    }


    override fun onCreate() {
        super.onCreate()

        instance = this

        appContext = applicationContext


    }


    companion object {

        @get:Synchronized
        var instance: MyApplication? = null
            private set
        var appContext: Context? = null
            private set

        val sharedPreferences: SharedPreferences by lazy {
            appContext!!.getSharedPreferences(
                "zocket",
                Context.MODE_PRIVATE
            )
        }

    }
}