package com.example.miniproyectoi.view.dialogos

import android.app.AlertDialog
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.miniproyectoi.R
import com.example.miniproyectoi.databinding.DialogDeleteBinding
import com.example.miniproyectoi.model.Challenge
import com.example.miniproyectoi.viewmodel.ChallengeViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object DialogDeleteChallenge {
    fun showDialog(context: Context, challengeViewModel: ChallengeViewModel, challenge: Challenge) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_delete, null)
        val tvChallengeName: TextView = dialogView.findViewById(R.id.tv_challenge_name)

        // Setear el texto personalizado con el nombre del reto
        tvChallengeName.text = "Se eliminará el reto ${challenge.name}. ¿Deseas continuar?"

        val dialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .setCancelable(true)
            .create()

        // Configurar los botones del diálogo
        dialogView.findViewById<Button>(R.id.btn_cancel_delete).setOnClickListener {
            dialog.dismiss()
        }

        dialogView.findViewById<Button>(R.id.btn_save_delete).setOnClickListener {
            // Llamar a la función de eliminación en el ViewModel
            challengeViewModel.deleteChallenge(challenge)
            dialog.dismiss()
        }

        dialog.show()
    }
}
