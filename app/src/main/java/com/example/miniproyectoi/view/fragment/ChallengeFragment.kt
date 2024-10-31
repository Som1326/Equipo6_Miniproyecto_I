package com.example.miniproyectoi.view.fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.miniproyectoi.databinding.FragmentChallengeBinding
import com.example.miniproyectoi.view.adapter.ChallengeAdapter

import androidx.navigation.fragment.findNavController
import com.example.miniproyectoi.view.dialogos.DialogAddChallenge
import com.example.miniproyectoi.view.dialogos.DialogDeleteChallenge
import com.example.miniproyectoi.view.dialogos.DialogEditChallenge
import com.example.miniproyectoi.viewmodel.ChallengeViewModel

class ChallengeFragment : Fragment() {
    private lateinit var binding: FragmentChallengeBinding
    private val challengeViewModel: ChallengeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChallengeBinding.inflate(inflater)
        binding.lifecycleOwner = this

        setUpCreateChallengeDialog()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerViewModel()
    }

    private fun setUpCreateChallengeDialog() {
        binding.btnAdd.setOnClickListener {
            DialogAddChallenge.showDialog(requireContext(), challengeViewModel)
        }
    }


    private fun observerViewModel(){
        observerListChallenge()
        observerProgress()
    }

    private fun observerListChallenge() {
        challengeViewModel.getListChallenge()
        challengeViewModel.listChallenge.observe(viewLifecycleOwner) { listChallenge ->
            val recycler = binding.recyclerview
            recycler.layoutManager = LinearLayoutManager(context)

            val adapter = ChallengeAdapter(
                listChallenge,
                findNavController(),
                onEditClick = { challenge ->
                    DialogEditChallenge.showDialog(requireContext(), challengeViewModel, challenge)
                },
                onDeleteClick = { challenge ->
                    DialogDeleteChallenge.showDialog(requireContext(), challengeViewModel, challenge)
                }
            )

            recycler.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }



    private fun observerProgress(){
        challengeViewModel.progresState.observe(viewLifecycleOwner){status ->
            binding.progress.isVisible = status
        }
    }
}
