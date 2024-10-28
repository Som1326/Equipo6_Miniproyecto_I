package com.example.miniproyectoi.view.fragment

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.media.AudioManager
import android.media.Image
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import android.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.example.miniproyectoi.R
import com.example.miniproyectoi.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

        binding.btnStart.setOnClickListener {
            Snackbar.make(binding.btnStart.findViewById(R.id.btn_start), "Boton presionado", Toast.LENGTH_LONG).show()
            lifecycleScope.launch{
                binding.btnStart.playAnimation()
                delay(binding.btnStart.duration/3)  // Espera el tiempo de la animación
                findNavController().navigate(R.id.action_homeFragment_to_rateFragment)
            }
        }

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

        toolbar.findViewById<ImageView>(R.id.rate_toolbar).setOnClickListener {
            Snackbar.make(toolbar, "Rate Option", Toast.LENGTH_LONG).show()
            toolbar.findViewById<ImageView>(R.id.rate_toolbar).startAnimation(animation)
            lifecycleScope.launch{
                delay(animation.duration)  // Espera el tiempo de la animación
                findNavController().navigate(R.id.action_homeFragment_to_rateFragment)
            }
        }

        val volumeButton = toolbar.findViewById<ImageView>(R.id.volume_toolbar)
        volumeButton.setOnClickListener {
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.pause()
                volumeButton.setImageResource(R.drawable.baseline_volume_off_24) // Cambia al icono de "play"
                Snackbar.make(toolbar, "Music Paused", Snackbar.LENGTH_LONG).show()
            } else {
                mediaPlayer?.start()
                volumeButton.setImageResource(R.drawable.baseline_volume_up_24) // Cambia al icono de "pausa"
                Snackbar.make(toolbar, "Music Playing", Snackbar.LENGTH_LONG).show()
            }
        }

        toolbar.findViewById<ImageView>(R.id.game_toolbar).setOnClickListener {
            Snackbar.make(toolbar, "Instructions Option", Toast.LENGTH_LONG).show()
            lifecycleScope.launch{
                delay(animation.duration)  // Espera el tiempo de la animación
                findNavController().navigate(R.id.action_homeFragment_to_instructionsFragment)
            }
        }

        toolbar.findViewById<ImageView>(R.id.add_toolbar).setOnClickListener {
            Snackbar.make(toolbar, "Add Option", Toast.LENGTH_LONG).show()
            lifecycleScope.launch{
                delay(animation.duration)  // Espera el tiempo de la animación
                findNavController().navigate(R.id.action_homeFragment_to_challengeFragment)
            }
        }

        toolbar.findViewById<ImageView>(R.id.share_toolbar).setOnClickListener {
            Snackbar.make(toolbar, "Share Option", Toast.LENGTH_LONG).show()
        }
    }
}