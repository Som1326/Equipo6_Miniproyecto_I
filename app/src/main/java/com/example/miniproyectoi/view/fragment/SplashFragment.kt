package com.example.miniproyectoi.view.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.miniproyectoi.R
import com.example.miniproyectoi.databinding.FragmentHomeBinding
import com.example.miniproyectoi.databinding.FragmentSplashBinding
import androidx.navigation.fragment.findNavController
import com.example.miniproyectoi.view.LoginActivity
import com.example.miniproyectoi.view.MainActivity
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {
    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        binding.bottleAnimation.playAnimation()
        startSplashTimer()
        return binding.root
    }
    private fun startSplashTimer() {
        Handler(Looper.getMainLooper()).postDelayed({
            val sharedPreferences = requireContext().getSharedPreferences("shared", Context.MODE_PRIVATE)
            val email = sharedPreferences.getString("email", null)
            val currentUser = FirebaseAuth.getInstance().currentUser

            if (email != null && currentUser != null) {
                // El usuario ya está autenticado, navega directamente al HomeFragment
                findNavController().navigate(R.id.homeFragment)
            } else {
                // Usuario no autenticado, redirige a la LoginActivity
                startActivity(Intent(requireContext(), LoginActivity::class.java))
                requireActivity().finish()
            }

        }, 5000) // 5 segundos
    }
    override fun onDestroyView() {
        super.onDestroyView()
        // Detener la animación si el fragmento se destruye
        binding.bottleAnimation.cancelAnimation()
    }
}