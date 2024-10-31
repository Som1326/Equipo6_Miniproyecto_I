package com.example.miniproyectoi.view.fragment

import android.animation.ObjectAnimator
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.miniproyectoi.R
import com.example.miniproyectoi.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.activity.OnBackPressedCallback
import kotlin.random.Random
import android.animation.Animator
import android.content.Context
import com.example.miniproyectoi.view.dialogos.DialogoMostrarReto.Companion.showDialogPersonalizado
import androidx.fragment.app.viewModels
import com.example.miniproyectoi.viewmodel.InventoryViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var mediaPlayer: MediaPlayer? = null
    private var lastRotation = 0f
    private val inventoryViewModel: InventoryViewModel by viewModels()
    private var playMusic = true
    private var volumeUp = true

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
        val toolbar = binding.contentToolbar.toolbar
        val volumeButton = toolbar.findViewById<ImageView>(R.id.volume_toolbar)
        handleOnBackPressed()
        setupToolbar()
        setupBtnStart()
        volumeIcon(volumeButton)
        binding.btnStart.playAnimation()
    }

    override fun onResume() {
        super.onResume()
        val toolbar = binding.contentToolbar.toolbar
        val volumeButton = toolbar.findViewById<ImageView>(R.id.volume_toolbar)

        if (playMusic && volumeUp){
            volumeIcon(volumeButton)
            setupMusic(toolbar)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        releaseMediaPlayer()
    }

    private fun releaseMediaPlayer() {
        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun setupBtnStart(){
        val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.zoom)
        binding.btnStart.setOnClickListener {
            Snackbar.make(binding.btnStart.findViewById(R.id.btn_start), "Boton presionado", Toast.LENGTH_LONG).show()

            lifecycleScope.launch{
                binding.btnStart.startAnimation(animation)
                delay(binding.btnStart.duration/3)
                startBottleRotation()
            }
        }
    }

    private fun setupMusic(toolbar: androidx.appcompat.widget.Toolbar) {
        if (playMusic) {
            mediaPlayer = MediaPlayer.create(requireContext(), R.raw.runaway)
            mediaPlayer?.isLooping = true
            mediaPlayer?.start()
            Snackbar.make(toolbar, "Music Playing", Snackbar.LENGTH_LONG).show()
        } else {
            releaseMediaPlayer()
            Snackbar.make(toolbar, "Music Paused", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun volumeIcon(volumeButton: ImageView){
        if (volumeUp){
            volumeButton.setImageResource(R.drawable.baseline_volume_up_24)
        } else {
            volumeButton.setImageResource(R.drawable.baseline_volume_off_24)
        }
    }

    private fun setupToolbar() {
        val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.zoom)
        val toolbar = binding.contentToolbar.toolbar
        val volumeButton = toolbar.findViewById<ImageView>(R.id.volume_toolbar)
        val optionRate = toolbar.findViewById<ImageView>(R.id.rate_toolbar)
        val optionGame = toolbar.findViewById<ImageView>(R.id.game_toolbar)
        val optionAdd = toolbar.findViewById<ImageView>(R.id.add_toolbar)
        val optionShare = toolbar.findViewById<ImageView>(R.id.share_toolbar)

        optionRate.setOnClickListener {
            optionRate.startAnimation(animation)
            if (mediaPlayer?.isPlaying == true){
                mediaPlayer?.stop()
            }
            lifecycleScope.launch{
                delay(animation.duration)  // Espera el tiempo de la animación
                findNavController().navigate(R.id.action_homeFragment_to_rateFragment)
            }
        }

        volumeButton.setOnClickListener {
            playMusic = !playMusic
            volumeUp = !volumeUp
            volumeButton.startAnimation(animation)
            volumeIcon(volumeButton)
            setupMusic(toolbar)
        }

        optionGame.setOnClickListener {
            optionGame.startAnimation(animation)
            if (mediaPlayer?.isPlaying == true){
                mediaPlayer?.stop()
            }
            lifecycleScope.launch{
                delay(animation.duration)
                findNavController().navigate(R.id.action_homeFragment_to_instructionsFragment)
            }
        }

        optionAdd.setOnClickListener {
            optionAdd.startAnimation(animation)
            if (mediaPlayer?.isPlaying == true){
                mediaPlayer?.stop()
            }
            lifecycleScope.launch{
                delay(animation.duration)
                findNavController().navigate(R.id.action_homeFragment_to_challengeFragment)
            }
        }

        optionShare.setOnClickListener {
            optionShare.startAnimation(animation)
            if (mediaPlayer?.isPlaying == true){
                mediaPlayer?.stop()
            }
            Share()
        }
    }

    private fun handleOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
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
        val newRotation = lastRotation + Random.nextInt(720, 1440) // De 2 a 4 vueltas completas

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

        binding.btnStart.visibility=View.INVISIBLE
        binding.txtPresioname.visibility=View.INVISIBLE
        rotateAnimation.start()
    }

    private fun onRotationEnd(newRotation: Float) {
        lastRotation = newRotation % 360
        showCountdown()
    }

    private fun showCountdown() {
        binding.txtCounter.visibility = View.VISIBLE
        lifecycleScope.launch {
            for (i in 3 downTo 0) {
                binding.txtCounter.text = i.toString()
                delay(1000)
            }
            binding.txtCounter.visibility = View.GONE
            binding.btnStart.visibility = View.VISIBLE
            binding.txtPresioname.visibility=View.VISIBLE

            showDialogPersonalizado(binding.root.context, inventoryViewModel)
        }
    }



}
