package com.example.miniproyectoi.view.dialogos

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import com.example.miniproyectoi.R
import com.example.miniproyectoi.databinding.DialogDeleteBinding
import com.example.miniproyectoi.model.Challenge
import com.example.miniproyectoi.viewmodel.ChallengeViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DialogDeleteChallenge {
    companion object {
        fun showDialog(
            context: Context,
            viewModel: ChallengeViewModel,
            challenge: Challenge
        ) {
            val binding = DialogDeleteBinding.inflate(LayoutInflater.from(context))
            val dialog = MaterialAlertDialogBuilder(context)
                .setView(binding.root)
                .setCancelable(false)
                .create()

            binding.btnSaveDelete.setOnClickListener {
                viewModel.deleteChallenge(challenge)
                dialog.dismiss()
            }

            binding.btnCancelDelete.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }
    }
}