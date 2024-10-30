package com.example.miniproyectoi.view.viewholder

import android.os.Bundle
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.miniproyectoi.R
import com.example.miniproyectoi.databinding.ItemChallengeBinding
import com.example.miniproyectoi.model.Challenge

class ChallengeViewHolder(binding: ItemChallengeBinding, navController: NavController) :
    RecyclerView.ViewHolder(binding.root) {
        val bindingItem = binding
        val navController = navController
    fun setItemChallenge(challenge: Challenge) {
        bindingItem.nameChallenge.text = challenge.name

        bindingItem.cvChallenge.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("clave", challenge)
            navController.navigate(R.id.challengeFragment, bundle)
        }
    }
}