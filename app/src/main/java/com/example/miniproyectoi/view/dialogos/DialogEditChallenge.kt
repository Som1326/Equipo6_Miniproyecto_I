package com.example.miniproyectoi.view.dialogos

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import com.example.miniproyectoi.R
import com.example.miniproyectoi.databinding.DialogEditBinding
import com.example.miniproyectoi.model.Challenge
import com.example.miniproyectoi.viewmodel.ChallengeViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DialogEditChallenge {
    companion object {
        fun showDialog(
            context: Context,
            viewModel: ChallengeViewModel,
            challenge: Challenge
        ) {
            val binding = DialogEditBinding.inflate(LayoutInflater.from(context))

            // Configura el campo de texto con el nombre actual del desafío
            binding.etChallengeName.setText(challenge.name)

            val dialog = MaterialAlertDialogBuilder(context)
                .setView(binding.root)
                .setCancelable(false)
                .create()

            // Configuración inicial del botón Guardar
            binding.btnSaveEdit.apply {
                isEnabled = true
                setBackgroundColor(ContextCompat.getColor(context, R.color.orange))
            }

            // Listener para habilitar/deshabilitar el botón Guardar
            binding.etChallengeName.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    val isNotEmpty = !s.isNullOrEmpty()
                    binding.btnSaveEdit.apply {
                        isEnabled = isNotEmpty
                        setBackgroundColor(
                            if (isNotEmpty) ContextCompat.getColor(context, R.color.orange)
                            else ContextCompat.getColor(context, R.color.grey)
                        )
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })

            // Botón Guardar
            binding.btnSaveEdit.setOnClickListener {
                val updatedName = binding.etChallengeName.text.toString()
                if (updatedName.isNotEmpty()) {
                    challenge.name = updatedName
                    viewModel.updateChallenge(challenge)
                    dialog.dismiss()
                }
            }

            // Botón Cancelar
            binding.btnCancelEdit.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }
    }
}
