package com.example.miniproyectoi.view.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.miniproyectoi.R
import com.example.miniproyectoi.databinding.DialogCustomBinding
import com.example.miniproyectoi.databinding.FragmentChallengeBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ChallengeFragment : Fragment() {

    private lateinit var binding: FragmentChallengeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChallengeBinding.inflate(inflater)
        binding.lifecycleOwner = this

        setupDialog()

        return binding.root
    }

    private fun setupDialog() {
        binding.btnAdd.setOnClickListener {
            val dialogBinding = DialogCustomBinding.inflate(layoutInflater)

            val dialog = MaterialAlertDialogBuilder(requireContext())
                .setView(dialogBinding.root)
                .setCancelable(false) // No se cierra al tocar fuera
                .create()

            // Botón "Guardar" deshabilitado inicialmente
            dialogBinding.btnSave.apply {
                isEnabled = false
                setBackgroundColor(resources.getColor(R.color.grey))
            }

            // Listener para detectar cambios en el texto del EditText
            dialogBinding.etChallengeName.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    val isNotEmpty = !s.isNullOrEmpty()
                    dialogBinding.btnSave.apply {
                        isEnabled = isNotEmpty
                        // Cambia el color según el estado
                        setBackgroundColor(
                            if (isNotEmpty) resources.getColor(R.color.orange)
                            else resources.getColor(R.color.grey)
                        )
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })

            // Botón "Cancelar" para cerrar el diálogo
            dialogBinding.btnCancel.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }
    }


    private fun showCustomDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_custom, null)

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(dialogView)
            .setCancelable(false)  // Evita que el diálogo se cierre al tocar fuera
            .create()

        // Botones del diálogo
        val btnCancel = dialogView.findViewById<Button>(R.id.btn_cancel)
        val btnSave = dialogView.findViewById<Button>(R.id.btn_save)

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        btnSave.setOnClickListener {
            val challengeName = dialogView.findViewById<EditText>(R.id.et_challenge_name).text.toString()
            if (challengeName.isNotEmpty()) {
                // Lógica para guardar el reto
                dialog.dismiss()
            } else {
                Toast.makeText(requireContext(), "Ingrese un nombre", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }
}
