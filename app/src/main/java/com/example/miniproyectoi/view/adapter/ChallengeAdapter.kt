package com.example.miniproyectoi.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.miniproyectoi.databinding.ItemChallengeBinding
import com.example.miniproyectoi.model.Challenge
import com.example.miniproyectoi.view.viewholder.ChallengeViewHolder

class ChallengeAdapter(private val listChallenge:MutableList<Challenge>, private val navController: NavController):RecyclerView.Adapter<ChallengeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeViewHolder {
        val binding = ItemChallengeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChallengeViewHolder(binding, navController)
    }

    override fun getItemCount(): Int {
        return listChallenge.size
    }

    override fun onBindViewHolder(holder: ChallengeViewHolder, position: Int) {
        val challenge = listChallenge[position]
        holder.setItemChallenge(challenge)
    }
}