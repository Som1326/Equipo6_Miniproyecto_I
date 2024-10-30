package com.example.miniproyectoi.view.dialogos

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.miniproyectoi.databinding.DialogoMostrarRetoBinding
import androidx.lifecycle.ViewModelProvider
import com.example.miniproyectoi.viewmodel.ChallengeViewModel

class DialogoMostrarReto {
    companion object{
        fun showDialogPersonalizado(
            context: Context,
            viewModel: ChallengeViewModel // Pasamos el ViewModel como parámetro
        ) {
            val inflater = LayoutInflater.from(context)
            val binding = DialogoMostrarRetoBinding.inflate(inflater)

            val alertDialog = AlertDialog.Builder(context).create()
            alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            alertDialog.setCancelable(false)
            alertDialog.setView(binding.root)

            // Llama a la función traerimagenapi para mostrar imagen y texto aleatorios
            traerimagenapi(binding, viewModel)

            binding.btncerrar.setOnClickListener {
                alertDialog.dismiss()
            }
            alertDialog.show()
        }

        private fun traerimagenapi(binding: DialogoMostrarRetoBinding, viewModel: ChallengeViewModel) {
            viewModel.getListChallenge()
            viewModel.listChallenge.observeForever { lista ->
                // Imprime el tamaño de la lista
                println("Tamaño de la lista: ${lista.size}")


                // Selecciona una imagen aleatoria solo si la lista no está vacía
                if (lista.isNotEmpty()) {
                    val randomIndex = (0 until lista.size).random()
                    val product = lista[randomIndex]

                    Glide.with(binding.root.context)
                        // .load(product.img) // Asegúrate de que sea `product.img` para la URL de la imagen
                        // .into(binding.imgPokemon) // Confirma que `imgPokemon` es el ID correcto en el XML
                }
            }
        }


    }
}
