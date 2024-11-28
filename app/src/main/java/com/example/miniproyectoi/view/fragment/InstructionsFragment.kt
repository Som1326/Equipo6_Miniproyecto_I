package com.example.miniproyectoi.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieDrawable
import com.example.miniproyectoi.R
import com.example.miniproyectoi.databinding.FragmentHomeBinding
import com.example.miniproyectoi.databinding.FragmentInstructionsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class InstructionsFragment : Fragment() {

    private lateinit var binding: FragmentInstructionsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInstructionsBinding.inflate(inflater, container, false)
        remaneTittle("Reglas del Juego")
        binding.winAnimation.apply {
            repeatCount = LottieDrawable.INFINITE
            playAnimation()
        }
        customiseToolbar()
        binding.lifecycleOwner = this
        return binding.root
    }

    private fun customiseToolbar(){
        val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.zoom)
        val toolbar = binding.callToolBar.customToolbar
        val backButton = toolbar.findViewById<ImageView>(R.id.back_toolbar)

        backButton.setOnClickListener {
            backButton.startAnimation(animation)
            findNavController().navigate(R.id.action_rateFragment_to_homeFragment)
        }
    }

    private fun remaneTittle(newTitle: String){
        val textReplace = binding.callToolBar.toolbarTittle
        textReplace.text = newTitle
    }
}