package com.example.miniproyectoi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.miniproyectoi.model.UserRequest
import com.example.miniproyectoi.model.UserResponse
import com.example.miniproyectoi.repository.LoginRepository
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel: ViewModel() {
    private val repository = LoginRepository()
    private val _isRegister = MutableLiveData<UserResponse>()
    val isRegister: LiveData<UserResponse> = _isRegister

    fun registerUser(userRequest: UserRequest) {
        repository.registerUser(userRequest) { userResponse ->
            _isRegister.value = userResponse
        }
    }
    fun loginUser(email: String, pass: String, isLogin: (Boolean) -> Unit) {
        if (email.isNotEmpty() && pass.isNotEmpty()) {
            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        isLogin(true)
                    } else {
                        isLogin(false)
                    }
                }
                .addOnFailureListener {
                    isLogin(false)
                }
        } else {
            isLogin(false)
        }
    }
    fun sesion(email: String?, isEnableView: (Boolean) -> Unit) {
        if (email != null && FirebaseAuth.getInstance().currentUser != null) {
            isEnableView(true)
        } else {
            isEnableView(false)
        }
    }
}