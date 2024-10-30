package com.example.miniproyectoi.view.fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.miniproyectoi.R
import com.example.miniproyectoi.databinding.FragmentChallengeBinding
import com.example.miniproyectoi.view.adapter.ChallengeAdapter

import android.text.Editable
import android.text.TextWatcher
import androidx.navigation.fragment.findNavController
import com.example.miniproyectoi.databinding.DialogCustomBinding
import com.example.miniproyectoi.viewmodel.ChallengeViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ChallengeFragment : Fragment() {
    private lateinit var binding: FragmentChallengeBinding
    private val challengeViewModel: ChallengeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChallengeBinding.inflate(inflater)
        binding.lifecycleOwner = this

        setupDialog()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerViewModel()
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

    private fun observerViewModel(){
        observerListChallenge()
        observerProgress()
    }

    private fun observerListChallenge(){
        challengeViewModel.getListChallenge()
        challengeViewModel.listChallenge.observe(viewLifecycleOwner){ listChallenge ->
            val recycler = binding.recyclerview
            val layoutManager = LinearLayoutManager(context)
            recycler.layoutManager = LinearLayoutManager(context)
            val adapter = ChallengeAdapter(listChallenge, findNavController())
            recycler.adapter =adapter
            adapter.notifyDataSetChanged()
        }
    }

    private fun observerProgress(){
        challengeViewModel.progresState.observe(viewLifecycleOwner){status ->
            binding.progress.isVisible = status
        }
    }

}
