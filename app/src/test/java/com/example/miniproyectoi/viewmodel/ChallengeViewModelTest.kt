package com.example.miniproyectoi.viewmodel

import android.app.Application
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.miniproyectoi.respository.ChallengeRepository
import kotlinx.coroutines.CoroutineDispatcher

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.any
import org.mockito.Mockito.anyObject
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class ChallengeViewModelTest {

//    @get:Rule
//    val rule = InstantTaskExecutorRule()
//    private lateinit var challengeRepository: ChallengeRepository
//    private lateinit var challengeViewModel: ChallengeViewModel
//
//    @Before
//    fun setUp() {
//        challengeRepository = mock(ChallengeRepository::class.java)
//        challengeViewModel = ChallengeViewModel(challengeRepository)
//    }
//
//
//    @Test
//    fun `test método getListChallenge`()= runBlocking {
//
//    }

}