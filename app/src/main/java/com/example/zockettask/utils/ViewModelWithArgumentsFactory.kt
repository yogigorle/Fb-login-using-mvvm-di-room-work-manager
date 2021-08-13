package com.example.zockettask.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.JsonElement
import org.kodein.di.DKodein
import org.kodein.di.generic.instanceOrNull
import java.lang.reflect.Constructor

class ViewModelWithArgumentsFactory(private val args: JsonElement?) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        try {
            return if (args == null) {
                val constructor: Constructor<T> =
                    modelClass.getDeclaredConstructor()
                constructor.newInstance()
            } else {
                val constructor: Constructor<T> =
                    modelClass.getDeclaredConstructor(JsonElement::class.java)
                constructor.newInstance(args)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }
}