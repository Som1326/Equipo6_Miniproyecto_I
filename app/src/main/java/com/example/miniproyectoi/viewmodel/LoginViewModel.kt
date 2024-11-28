package com.example.miniproyectoi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.miniproyectoi.model.UserRequest
import com.example.miniproyectoi.model.UserResponse
import com.example.miniproyectoi.repository.LoginRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository
): ViewModel() {

    private val _isRegister = MutableLiveData<UserResponse>()
    val isRegister: LiveData<UserResponse> = _isRegister

    private val _passwordError = MutableLiveData<String?>()
    val passwordError: LiveData<String?> = _passwordError

    private val _isFormValid = MutableLiveData(false)
    val isFormValid: LiveData<Boolean> = _isFormValid

    fun validatePassword(password: String) {
        when {
//            password.isEmpty() -> _passwordError.value = "El campo no puede estar vacío"
            password.length < 6 -> _passwordError.value = "Mínimo 6 dígitos"
            password.length > 10 -> _passwordError.value = "Máximo 10 dígitos"
            !password.all { it.isDigit() } -> _passwordError.value = "Solo se permiten números"
            else -> _passwordError.value = null // Sin errores
        }
    }

    fun validateForm(email: String, password: String) {
        _isFormValid.value = email.isNotEmpty() && password.isNotEmpty()
    }

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