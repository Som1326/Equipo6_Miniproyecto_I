package com.example.miniproyectoi.view.fragment

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
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
import androidx.activity.OnBackPressedCallback
import kotlin.random.Random
import android.animation.Animator

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var mediaPlayer: MediaPlayer? = null
    private var lastRotation = 0f


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        binding.lifecycleOwner = this
        handleOnBackPressed()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        binding.txtCounter

        binding.btnStart.setOnClickListener {
            Snackbar.make(binding.btnStart.findViewById(R.id.btn_start), "Boton presionado", Toast.LENGTH_LONG).show()

            startBottleRotation()

            lifecycleScope.launch{
                binding.btnStart.playAnimation()
                delay(binding.btnStart.duration/3)  // Espera el tiempo de la animación
//                findNavController().navigate(R.id.action_homeFragment_to_rateFragment)
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
        val OptionShare = toolbar.findViewById<ImageView>(R.id.share_toolbar)

        OptionShare.setOnClickListener{ Share()}

    }

    private fun handleOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // Cierra la app al presionar atrás desde el Home
                    requireActivity().finish()
                }
            }
        )
    }

    private fun Share() {
        val appTitle = "App pico botella"
        val slogan = "Solo los valientes lo juegan !!"
        val appUrl = "https://play.google.com/store/apps/details?id=com.nequi.MobileApp&hl=es_419&gl=es"

        val shareText = "$appTitle\n$slogan\n$appUrl"

        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
        }

        val chooser = Intent.createChooser(intent, "Compartir vía")
        startActivity(chooser)
    }
    private fun startBottleRotation() {
        // aqui generamos un angulo para que gire en la misma posicion que habia quedado despues del giro
        val newRotation = lastRotation + Random.nextInt(720, 1440) // De 2 a 4 vueltas completas

        // Creamos y configuramos la animación de rotación
        val rotateAnimation = ObjectAnimator.ofFloat(binding.imgBottle, "rotation", lastRotation, newRotation)
        rotateAnimation.duration = Random.nextLong(3000, 5000) // Duración entre 3 y 5 segundos

        rotateAnimation.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}

            override fun onAnimationEnd(animation: Animator) {
                onRotationEnd(newRotation) // Llama a la función al terminar la rotación
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })

        // Inicia la animación de rotación
        binding.btnStart.visibility=View.INVISIBLE
        binding.txtPresioname.visibility=View.INVISIBLE
        rotateAnimation.start()
    }

    private fun onRotationEnd(newRotation: Float) {
        // Guarda la posición final de la botella para la próxima rotación
        lastRotation = newRotation % 360
        showCountdown() // Muestra la cuenta regresiva
    }

    private fun showCountdown() {
        binding.txtCounter.visibility = View.VISIBLE // se hace visible  el contador
        lifecycleScope.launch {
            for (i in 3 downTo 0) {
                binding.txtCounter.text = i.toString()
                delay(1000) // Esperamos 1 segundo para cada número de la cuenta regresiva
            }
            binding.txtCounter.visibility = View.GONE // Ocultamos el contador cuando llega a 0
            binding.btnStart.visibility = View.VISIBLE // Muestramos el botón nuevamente
            binding.txtPresioname.visibility=View.VISIBLE
        }
    }


}
