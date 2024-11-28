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
import android.widget.Button
import com.example.miniproyectoi.view.dialogos.DialogoMostrarReto.Companion.showDialogPersonalizado
import androidx.fragment.app.viewModels
import com.example.miniproyectoi.view.LoginActivity
import com.example.miniproyectoi.view.MainActivity
import com.example.miniproyectoi.viewmodel.ChallengeViewModel
import com.example.miniproyectoi.viewmodel.InventoryViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.internal.wait

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var mediaPlayer: MediaPlayer? = null
    private var mediaPlayer2: MediaPlayer? = null
    private var lastRotation = 0f
    private val inventoryViewModel: InventoryViewModel by viewModels()
    private val challengeViewModel: ChallengeViewModel by viewModels()
    private var playMusic = true
    private var volumeUp = true
    private var _url = "https://play.google.com/store/apps/details?id=com.nequi.MobileApp&hl=es_419&gl=es"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        loadMusicState() // Carga el estado guardado de la música
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
    override fun onPause() {
        super.onPause()
        releaseMediaPlayer() // Libera el recurso de mediaPlayer al pausar
    }

    override fun onResume() {
        super.onResume()
        if (playMusic && volumeUp) {
            setupMusic() // Solo reproduce si está en modo de reproducción y con el volumen activado
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
            if (playMusic){
                releaseMediaPlayer()
            }
            lifecycleScope.launch{
                binding.btnStart.startAnimation(animation)
                mediaPlayer2 = MediaPlayer.create(requireContext(), R.raw.soundbottle)
                mediaPlayer2?.isLooping = true
                mediaPlayer2?.start()
                delay(binding.btnStart.duration/3)
                startBottleRotation()
            }
        }
    }

    private fun setupMusic() {
        if (playMusic) {
            mediaPlayer = MediaPlayer.create(requireContext(), R.raw.soundtrack)
            mediaPlayer?.isLooping = true
            mediaPlayer?.start()
        } else {
            releaseMediaPlayer()
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
        val optionLogout = toolbar.findViewById<ImageView>(R.id.logout_toolbar)

        optionRate.setOnClickListener {
            optionRate.startAnimation(animation)
            if (playMusic){
                mediaPlayer?.stop()
            }
            lifecycleScope.launch {
                delay(animation.duration)  // Espera el tiempo de la animación
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = android.net.Uri.parse(_url)
                }
                startActivity(intent)
            }
        }

        volumeButton.setOnClickListener {
            playMusic = !playMusic
            volumeUp = !volumeUp
            volumeButton.startAnimation(animation)
            volumeIcon(volumeButton)
            setupMusic()
            saveMusicState() // Guarda el estado cada vez que cambia
        }

        optionGame.setOnClickListener {
            optionGame.startAnimation(animation)
            if (playMusic){
                mediaPlayer?.stop()
            }
            lifecycleScope.launch{
                delay(animation.duration)
                findNavController().navigate(R.id.action_homeFragment_to_instructionsFragment)
            }
        }

        optionAdd.setOnClickListener {
            optionAdd.startAnimation(animation)
            if (playMusic){
                mediaPlayer?.stop()
            }
            lifecycleScope.launch{
                delay(animation.duration)
                findNavController().navigate(R.id.action_homeFragment_to_challengeFragment)
            }
        }

        optionShare.setOnClickListener {
            optionShare.startAnimation(animation)
            if (playMusic){
                mediaPlayer?.stop()
            }
            Share()
        }

        optionLogout.setOnClickListener {
            optionLogout.startAnimation(animation)
            if (playMusic){
                mediaPlayer?.stop()
            }
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
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
        lifecycleScope.launch {
            val newRotation = lastRotation + Random.nextInt(720, 1440) // De 2 a 4 vueltas completas

            val rotateAnimation =
                ObjectAnimator.ofFloat(binding.imgBottle, "rotation", lastRotation, newRotation)
            rotateAnimation.duration = Random.nextLong(3000, 5000) // Duración entre 3 y 5 segundos

            rotateAnimation.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}

                override fun onAnimationEnd(animation: Animator) {
                    onRotationEnd(newRotation) // Llama a la función al terminar la rotación
                }

                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}
            })

            binding.btnStart.visibility = View.INVISIBLE
            binding.txtPresioname.visibility = View.INVISIBLE
            rotateAnimation.start()
            delay(rotateAnimation.duration)
            mediaPlayer2?.release()
            mediaPlayer2 = null
        }
    }

    private fun onRotationEnd(newRotation: Float) {
        lastRotation = newRotation % 360
        showCountdown()
    }

    private fun showCountdown() {
        val toolbar = binding.contentToolbar.toolbar
        binding.txtCounter.visibility = View.VISIBLE
        lifecycleScope.launch {
            for (i in 3 downTo 0) {
                binding.txtCounter.text = i.toString()
                delay(1000)
            }
            binding.txtCounter.visibility = View.GONE
            binding.btnStart.visibility = View.VISIBLE
            binding.txtPresioname.visibility=View.VISIBLE

            showDialogPersonalizado(
                binding.root.context,
                inventoryViewModel,
                challengeViewModel,
                ::setupMusic // Pasamos la función como referencia
            )
        }
    }
    private fun saveMusicState() {
        val sharedPreferences = requireContext().getSharedPreferences("music_preferences", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putBoolean("playMusic", playMusic)
            putBoolean("volumeUp", volumeUp)
            apply()
        }
    }
    private fun loadMusicState() {
        val sharedPreferences = requireContext().getSharedPreferences("music_preferences", Context.MODE_PRIVATE)
        playMusic = sharedPreferences.getBoolean("playMusic", true)
        volumeUp = sharedPreferences.getBoolean("volumeUp", true)
    }



}