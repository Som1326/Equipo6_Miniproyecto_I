package com.example.miniproyectoi.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil

import com.example.miniproyectoi.R
import com.example.miniproyectoi.databinding.ActivityLoginBinding
import com.example.miniproyectoi.model.UserRequest
import com.example.miniproyectoi.viewmodel.LoginViewModel

class LoginActivity: AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        sharedPreferences = getSharedPreferences("shared", Context.MODE_PRIVATE)
        sesion()
        setup()
        viewModelObservers()
    }
    private fun viewModelObservers() {
        observerIsRegister()
    }
    private fun observerIsRegister() {
        loginViewModel.isRegister.observe(this) { userResponse ->
            if (userResponse.isRegister) {
                Toast.makeText(this, userResponse.message, Toast.LENGTH_SHORT).show()
                sharedPreferences.edit().putString("email",userResponse.email).apply()
                // En lugar de ir a goToHome(), vamos directamente a loginUser()
                loginUser(userResponse.email ?: "", binding.etPass.text.toString())
            } else {
                Toast.makeText(this, userResponse.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun setup() {
        binding.tvRegister.setOnClickListener {
            registerUser()
        }

        binding.btnLogin.setOnClickListener {
            loginUser()
        }
    }
    private fun registerUser() {
        val email = binding.etEmail.text.toString()
        val pass = binding.etPass.text.toString()
        val userRequest = UserRequest(email, pass)

        if (email.isNotEmpty() && pass.isNotEmpty()) {
            loginViewModel.registerUser(userRequest)
        } else {
            Toast.makeText(this, "Campos Vacíos", Toast.LENGTH_SHORT).show()
        }
    }
    private fun goToHome(){
        val intent = Intent (this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
    private fun loginUser(email: String = "", pass: String = ""){
        val finalEmail = if (email.isEmpty()) binding.etEmail.text.toString() else email
        val finalPass = if (pass.isEmpty()) binding.etPass.text.toString() else pass

        loginViewModel.loginUser(finalEmail, finalPass){ isLogin ->
            if (isLogin){
                sharedPreferences.edit().putString("email",email).apply()
                goToHome()
            }else {
                Toast.makeText(this, "Login incorrecto", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun sesion(){
        val email = sharedPreferences.getString("email",null)
        loginViewModel.sesion(email){ isEnableView ->
            if (isEnableView){
                binding.clContenedor.visibility = View.INVISIBLE
                goToHome()
            }
        }
    }
}