package com.example.miniproyectoi.view.dialogos

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import com.example.miniproyectoi.R
import com.example.miniproyectoi.databinding.DialogAddBinding
import com.example.miniproyectoi.viewmodel.ChallengeViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DialogAddChallenge {
    companion object {
        fun showDialog(
            context: Context,
            viewModel: ChallengeViewModel
        ) {
            val binding = DialogAddBinding.inflate(LayoutInflater.from(context))

            // Crear el diálogo
            val dialog = MaterialAlertDialogBuilder(context)
                .setView(binding.root)
                .setCancelable(false)
                .create()

            // Configuración inicial del botón Guardar
            binding.btnSave.apply {
                isEnabled = false
                setBackgroundColor(ContextCompat.getColor(context, R.color.grey))
            }

            // Listener para habilitar/deshabilitar el botón Guardar según el texto ingresado
            binding.etChallengeName.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    val isNotEmpty = !s.isNullOrEmpty()
                    binding.btnSave.apply {
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
            binding.btnSave.setOnClickListener {
                val challengeName = binding.etChallengeName.text.toString()
                if (challengeName.isNotEmpty()) {
                    viewModel.saveChallenge(challengeName)
                    dialog.dismiss()
                }
            }

            // Botón Cancelar
            binding.btnCancel.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }
    }
}
