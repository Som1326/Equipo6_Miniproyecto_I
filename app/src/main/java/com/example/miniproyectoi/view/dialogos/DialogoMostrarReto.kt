package com.example.miniproyectoi.view.dialogos

import android.content.Context
import android.view.LayoutInflater
import android.widget.Toolbar
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.miniproyectoi.databinding.DialogoMostrarRetoBinding
import androidx.lifecycle.ViewModelProvider
import com.example.miniproyectoi.viewmodel.InventoryViewModel // Importa tu ViewModel

class DialogoMostrarReto {
    companion object{
        fun showDialogPersonalizado(
            context: Context,
            viewModel: InventoryViewModel, // Pasamos el ViewModel como parámetro
            setupMusic: () -> Unit, // Pasamos la función como parámetro
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

            // Configura el listener para ejecutar setupMusic cuando se cierre el diálogo
            alertDialog.setOnDismissListener {
                setupMusic()
            }

            alertDialog.show()
        }

        private fun traerimagenapi(binding: DialogoMostrarRetoBinding, viewModel: InventoryViewModel) {
            viewModel.getProducts()
            viewModel.listProducts.observeForever { lista ->
                // Imprime el tamaño de la lista
                println("Tamaño de la lista: ${lista.size}")

                // Imprime cada elemento de la lista
                lista.forEachIndexed { index, product ->
                    println("Producto $index: id=${product.id}, img=${product.img}")
                }

                // Selecciona una imagen aleatoria solo si la lista no está vacía
                if (lista.isNotEmpty()) {
                    val randomIndex = (0 until lista.size).random()
                    val product = lista[randomIndex]

                    Glide.with(binding.root.context)
                        .load(product.img) // Asegúrate de que sea `product.img` para la URL de la imagen
                        .into(binding.imgPokemon) // Confirma que `imgPokemon` es el ID correcto en el XML
                }
            }
        }
    }
}
