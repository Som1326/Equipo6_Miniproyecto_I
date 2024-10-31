package com.example.miniproyectoi.view.viewholder

import android.os.Bundle
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.miniproyectoi.R
import com.example.miniproyectoi.databinding.ItemChallengeBinding
import com.example.miniproyectoi.model.Challenge

class ChallengeViewHolder(
    private val binding: ItemChallengeBinding,
    private val navController: NavController,
    private val onEditClick: (Challenge) -> Unit,  // Lambda para editar
    private val onDeleteClick: (Challenge) -> Unit // Lambda para eliminar
) : RecyclerView.ViewHolder(binding.root) {

    fun setItemChallenge(challenge: Challenge) {
        binding.nameChallenge.text = challenge.name

        // Navegación al hacer clic en el CardView
        binding.cvChallenge.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("clave", challenge)
            navController.navigate(R.id.challengeFragment, bundle)
        }

        // Listener para editar
        binding.btnEdit.setOnClickListener {
            onEditClick(challenge)  // Llama a la lambda de editar
        }

        // Listener para eliminar
        binding.btnDelete.setOnClickListener {
            onDeleteClick(challenge)  // Llama a la lambda de eliminar
        }
    }
}
