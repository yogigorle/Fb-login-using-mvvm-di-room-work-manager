package com.example.zockettask.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.zockettask.data.db.entities.FacebookUser
import com.example.zockettask.utils.Constants

@Database(entities = [FacebookUser::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): Dao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        //method to get single instance of database class
        fun getDatabase(context: Context): UserDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }


            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    Constants.databaseTableName
                )
                    .build()

                INSTANCE = instance
                return instance
            }

        }


    }
}