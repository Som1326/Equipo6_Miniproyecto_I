package com.example.miniproyectoi.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer

import com.example.miniproyectoi.R
import com.example.miniproyectoi.databinding.ActivityLoginBinding
import com.example.miniproyectoi.model.UserRequest
import com.example.miniproyectoi.viewmodel.LoginViewModel
import com.google.android.material.textfield.TextInputLayout

class LoginActivity: AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        val tilPass = findViewById<TextInputLayout>(R.id.tilPass)
        val etPass = findViewById<EditText>(R.id.etPass)

        sharedPreferences = getSharedPreferences("shared", Context.MODE_PRIVATE)
        sesion()
        setup()
        viewModelObservers(tilPass)
        setupFormValidation()
        setupPasswordValidation(tilPass, etPass)
    }

    private fun setupPasswordValidation(tilPass: TextInputLayout, etPass: EditText) {
        etPass.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loginViewModel.validatePassword(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupFormValidation() {
        binding.etEmail.addTextChangedListener { text ->
            loginViewModel.validateForm(
                email = text.toString(),
                password = binding.etPass.text.toString()
            )}

        binding.etPass.addTextChangedListener { text ->
            loginViewModel.validateForm(
                email = binding.etEmail.text.toString(),
                password = text.toString()
            )}
    }

    private fun viewModelObservers(tilPass: TextInputLayout) {
        observerIsRegister()
        observePasswordValidation(tilPass)
        observeFormValidation()
    }

    private fun observeFormValidation() {
        loginViewModel.isFormValid.observe(this) { isValid ->
            binding.btnLogin.apply {
                isEnabled = isValid
                setTextColor(
                    if (isValid) resources.getColor(R.color.white, null)
                    else resources.getColor(R.color.black, null)
                )
                setTypeface(null, if (isValid) android.graphics.Typeface.BOLD else android.graphics.Typeface.NORMAL)
            }

            binding.tvRegister.apply {
                isEnabled = isValid
                setTextColor(
                    if (isValid) resources.getColor(R.color.white, null)
                    else resources.getColor(R.color.color_btnRegistrarse, null)
                )
                setTypeface(null, if (isValid) android.graphics.Typeface.BOLD else android.graphics.Typeface.NORMAL)
            }
        }
    }

    private fun observePasswordValidation(tilPass: TextInputLayout) {
        loginViewModel.passwordError.observe(this, Observer { error ->
            if (error != null) {
                tilPass.error = error
                tilPass.boxStrokeColor = Color.RED
            } else {
                tilPass.error = null
                tilPass.boxStrokeColor = Color.WHITE
            }
        })
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
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("navigateTo", "homeFragment") // Indica que debe ir al HomeFragment
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