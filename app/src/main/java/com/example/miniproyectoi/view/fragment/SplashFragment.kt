package com.example.miniproyectoi.view.fragment

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
            // Navega al HomeFragment
            findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
        }, 5000) // 5 segundos
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Detener la animación si el fragmento se destruye
        binding.bottleAnimation.cancelAnimation()
    }


}