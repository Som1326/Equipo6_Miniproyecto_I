package com.example.miniproyectoi.repository
import com.example.miniproyectoi.model.UserRequest
import com.example.miniproyectoi.model.UserResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val firebaseAuth : FirebaseAuth
) {

    fun registerUser(userRequest: UserRequest, userResponse: (UserResponse) -> Unit) {
        if (userRequest.email.isEmpty() || userRequest.password.isEmpty()) {
            userResponse(UserResponse(
                isRegister = false,
                message = "Email y contraseña son requeridos"
            ))
            return
        }

        firebaseAuth.createUserWithEmailAndPassword(userRequest.email, userRequest.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    userResponse(UserResponse(
                        email = userRequest.email,
                        isRegister = true,
                        message = "Usuario registrado exitosamente"
                    ))
                } else {
                    when (task.exception) {
                        is FirebaseAuthUserCollisionException -> {
                            userResponse(UserResponse(
                                isRegister = false,
                                message = "El usuario ya existe"
                            ))
                        }
                        is FirebaseAuthWeakPasswordException -> {
                            userResponse(UserResponse(
                                isRegister = false,
                                message = "La contraseña debe tener al menos 6 caracteres"
                            ))
                        }
                        is FirebaseAuthInvalidCredentialsException -> {
                            userResponse(UserResponse(
                                isRegister = false,
                                message = "Email inválido"
                            ))
                        }
                        else -> {
                            userResponse(UserResponse(
                                isRegister = false,
                                message = "Error en el registro: ${task.exception?.message}"
                            ))
                        }
                    }
                }
            }
    }
}