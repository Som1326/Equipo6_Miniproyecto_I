package com.example.miniproyectoi.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miniproyectoi.model.Challenge
import com.example.miniproyectoi.respository.ChallengeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengeViewModel @Inject constructor(
    private val challengeRepository: ChallengeRepository
): ViewModel() {

    private val _listChallenge = MutableLiveData<MutableList<Challenge>>()
    val listChallenge: LiveData<MutableList<Challenge>> get() = _listChallenge

    private val _progresState = MutableLiveData(false)
    val progresState: LiveData<Boolean> = _progresState

    fun saveChallenge(challenge: String) {
        viewModelScope.launch {
            _progresState.value = true
            try {
                challengeRepository.saveChallenge(challenge)
                getListChallenge()
            } catch (e: Exception) {
                // Handle error, maybe add an error state
                Log.e("ChallengeViewModel", "Error saving challenge", e)
            } finally {
                _progresState.value = false
            }
        }
    }

    fun getListChallenge() {
        viewModelScope.launch {
            _progresState.value = true
            try {
                _listChallenge.value = challengeRepository.getListChallenge()
            } catch (e: Exception) {
                Log.e("ChallengeViewModel", "Error getting challenges", e)
                _listChallenge.value = mutableListOf()
            } finally {
                _progresState.value = false
            }
        }
    }

    fun deleteChallenge(challenge: Challenge) {
        viewModelScope.launch {
            _progresState.value = true
            try {
                challengeRepository.deleteChallenge(challenge)
                getListChallenge()
            } catch (e: Exception) {
                Log.e("ChallengeViewModel", "Error deleting challenge", e)
            } finally {
                _progresState.value = false
            }
        }
    }

    fun updateChallenge(challenge: Challenge) {
        viewModelScope.launch {
            _progresState.value = true
            try {
                challengeRepository.updateRepository(challenge)
                getListChallenge()
            } catch (e: Exception) {
                Log.e("ChallengeViewModel", "Error updating challenge", e)
            } finally {
                _progresState.value = false
            }
        }
    }
}