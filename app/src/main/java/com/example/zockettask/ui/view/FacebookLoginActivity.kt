package com.example.zockettask.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.zockettask.data.db.entities.FacebookUser
import com.example.zockettask.databinding.ActivityFacebookLoginBinding
import com.example.zockettask.ui.viewmodel.FacebookLoginViewModel
import com.example.zockettask.utils.*
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginBehavior
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import timber.log.Timber


class FacebookLoginActivity : AppCompatActivity() {

    lateinit var facebookLoginBinding: ActivityFacebookLoginBinding

    private val fbLoginViewModel: FacebookLoginViewModel by viewModel()

    private var callbackManager: CallbackManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        facebookLoginBinding = ActivityFacebookLoginBinding.inflate(layoutInflater)
        setContentView(facebookLoginBinding.root)

        callbackManager = CallbackManager.Factory.create()


        facebookLoginBinding.loginButton.setOnClickListener {
            signInFacebook()
        }

    }

    private fun signInFacebook() {
        LoginManager.getInstance().logOut()

        var email = ""
        var name = ""
        var accessToken = ""

        facebookLoginBinding.loginButton.loginBehavior = LoginBehavior.NATIVE_WITH_FALLBACK
//        facebookLoginBinding.loginButton.performClick()
        facebookLoginBinding.loginButton.setPermissions(
            listOf(
                "public_profile",
                "email",
            )
        )
        // Callback _registration
        facebookLoginBinding.loginButton.registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {

                    accessToken = loginResult.accessToken.token

                    try {

                        val request = GraphRequest.newMeRequest(
                            loginResult.accessToken
                        ) { userData, response ->


                            Timber.e("Fb_response $userData")

                            val userID = userData.getString("id") ?: ""
                            val profile_picture =
                                "https://graph.facebook.com/$userID/picture?type=large"
                            if (userData.has("email"))
                                email = userData.getString("email") ?: ""
                            if (userData.has("name"))
                                name = userData.getString("name") ?: ""


                            if (checkIfNotEmpty(
                                    userID,
                                    profile_picture,
                                    name,
                                    email
                                )
                            ) {
                                fbLoginViewModel.addUser(
                                    FacebookUser(
                                        userID,
                                        email,
                                        name,
                                        profile_picture,
                                        accessToken
                                    )
                                )
                                putString(Constants.fb_user_id, userID)
                                launchActivity<HomeActivity>()
                            } else {
                                showToast("some fields are missing..")
                            }


                        }

                        val parameters = Bundle()
                        parameters.putString("fields", "id,email,name")
                        request.parameters = parameters
                        request.executeAsync()

                    } catch (ex: Exception) {
                        ex.printStackTrace()
                    }
                }

                override fun onCancel() {
                    Timber.d("login fb onCancel")
                }

                override fun onError(exception: FacebookException) {
                    showToast("Something went wrong.")
                    Timber.d("login fb onError ${exception.printStackTrace()}")
                }
            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        try {
            callbackManager?.onActivityResult(requestCode, resultCode, data)
        } catch (e: java.lang.Exception) {
            Timber.d(e.printStackTrace().toString())
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun checkIfNotEmpty(
        userId: String,
        profilePic: String,
        name: String,
        email: String
    ): Boolean {
        return (userId.isNotEmpty() && profilePic.isNotEmpty() && name.isNotEmpty() && email.isNotEmpty())
    }
}