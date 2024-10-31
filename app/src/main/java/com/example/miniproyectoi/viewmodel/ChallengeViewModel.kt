package com.example.miniproyectoi.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.miniproyectoi.model.Challenge
import com.example.miniproyectoi.respository.ChallengeRepository
import kotlinx.coroutines.launch


class ChallengeViewModel(application: Application) : AndroidViewModel(application) {
    val context = getApplication<Application>()
    private val challengeRepository = ChallengeRepository(context)

    private val _listChallenge = MutableLiveData<MutableList<Challenge>>()
    val listChallenge: LiveData<MutableList<Challenge>> get() = _listChallenge

    private val _progresState = MutableLiveData(false)
    val progresState: LiveData<Boolean> = _progresState

    fun saveChallenge(challenge: String){
        viewModelScope.launch {
            _progresState.value = true
            try {
                challengeRepository.saveChallenge(challenge)
                _progresState.value = false
            } catch (e: Exception) {
                _progresState.value = false
            }
        }
    }

    fun getListChallenge(){
        viewModelScope.launch {
            _progresState.value = true
            try {
                _listChallenge.value = challengeRepository.getListChallenge()
                _progresState.value = false
            } catch (e: Exception){
                _progresState.value = false
            }
        }
    }

    fun deleteChallenge(challenge: Challenge){
        viewModelScope.launch {
            _progresState.value = true
            try {
                challengeRepository.deleteChallenge(challenge)
                _progresState.value = false
            } catch (e: Exception){
                _progresState.value = false
            }
        }
    }

    fun updateChallenge(challenge: Challenge){
        viewModelScope.launch {
            _progresState.value = true
            try {
                challengeRepository.updateRepository(challenge)
                _progresState.value = false
            } catch (e: Exception) {
                _progresState.value = false
            }
        }
    }
}