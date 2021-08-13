package com.example.zockettask.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import coil.load
import coil.transform.CircleCropTransformation
import com.example.zockettask.MyApplication
import com.example.zockettask.R
import com.google.gson.JsonElement

fun Context.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

inline fun <reified VM : ViewModel, T> T.viewModel(args: JsonElement? = null): Lazy<VM> where  T : AppCompatActivity {

    return lazy {
        ViewModelProvider(this, ViewModelWithArgumentsFactory(args = args)).get(VM::class.java)
    }


}

inline fun <reified T : Any> newIntent(context: Context): Intent = Intent(context, T::class.java)

inline fun <reified T : Any> Context.launchActivity(
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>(this)
    intent.init()
    startActivity(intent, options)
}

fun putString(key: String, value: String) {
    val sp = MyApplication.sharedPreferences
    sp.edit().putString(key, value).apply()
}

fun getString(key: String): String? {
    val sp = MyApplication.sharedPreferences
    return sp.getString(key, "")
}

@BindingAdapter("app:image_src")
fun ImageView.loadImage(path: String?) {
    try {
        if (!path.isNullOrEmpty()) {
            this.load(path) {
                transformations(CircleCropTransformation())
            }
        } else {
            this.load(R.drawable.profile_placeholder) {
                transformations(CircleCropTransformation())
            }
        }

    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}