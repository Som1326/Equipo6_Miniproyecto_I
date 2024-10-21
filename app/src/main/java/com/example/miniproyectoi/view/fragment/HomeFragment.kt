package com.example.miniproyectoi.view.fragment

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.miniproyectoi.R
import com.example.miniproyectoi.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()

        val button = binding.btnStart.findViewById<Button>(R.id.btn_start)
        val flickAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.flicker)
        button.startAnimation(flickAnimation)

        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.runaway)
        mediaPlayer?.isLooping = true
        mediaPlayer?.start()
        mediaPlayer?.setOnCompletionListener {
            releaseMediaPlayer()
        }
    }

    private fun releaseMediaPlayer() {
        mediaPlayer?.release() // Liberar recursos del MediaPlayer
        mediaPlayer = null // Establecer a null para evitar fugas de memoria
    }

    override fun onDestroy() {
        super.onDestroy()
        releaseMediaPlayer() // Asegurarse de liberar el MediaPlayer al destruir el fragmento
    }

    private fun setupToolbar() {
        val toolbar = binding.contentToolbar.toolbar
        val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.zoom)
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.rate_toolbar -> {
                    //Tarea a realizar
                    Snackbar.make(toolbar, "Rate Option", Toast.LENGTH_LONG).show()
                    toolbar.startAnimation(animation)
                    findNavController().navigate(R.id.action_homeFragment_to_rateFragment)
                    true
                }

                R.id.volume_toolbar -> {
                    //Tarea a realizar
                    Snackbar.make(toolbar, "Volume Option", Toast.LENGTH_LONG).show()
                    true
                }

                R.id.game_toolbar -> {
                    //Tarea a realizar
                    Snackbar.make(toolbar, "Instructions Option", Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.action_homeFragment_to_instructionsFragment)
                    true
                }

                R.id.add_toolbar -> {
                    //Tarea a realizar
                    Snackbar.make(toolbar, "Add Option", Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.action_homeFragment_to_challengeFragment)
                    true
                }

                R.id.share_toolbar -> {
                    //Tarea a realizar
                    Snackbar.make(toolbar, "Share Option", Toast.LENGTH_LONG).show()
                    true
                }

                else -> false
            }
        }

    }

}