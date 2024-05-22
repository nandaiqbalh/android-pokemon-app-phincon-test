package com.nandaiqbalh.pokemonapp.presentation.ui.auth.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nandaiqbalh.pokemonapp.data.local.datastore.auth.AuthDataStoreManager
import com.nandaiqbalh.pokemonapp.data.remote.model.auth.login.request.AuthLoginRequestBody
import com.nandaiqbalh.pokemonapp.data.remote.model.auth.login.response.AuthLoginRemoteResponse
import com.nandaiqbalh.pokemonapp.data.remote.repository.auth.login.AuthLoginRemoteRepository
import com.nandaiqbalh.pokemonapp.wrapper.Resource
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class LoginViewModelTest {

	// This rule makes sure that LiveData updates instantly
	@get:Rule
	var instantExecutorRule = InstantTaskExecutorRule()

	// We will be using TestCoroutineDispatcher
	private val testDispatcher = TestCoroutineDispatcher()

	private lateinit var viewModel: LoginViewModel
	private lateinit var repository: AuthLoginRemoteRepository
	private lateinit var authDataStoreManager: AuthDataStoreManager

	@Before
	fun setUp() {
		// Set the main dispatcher to TestCoroutineDispatcher
		Dispatchers.setMain(testDispatcher)

		repository = mockk()
		authDataStoreManager = mockk()
		viewModel = LoginViewModel(repository, authDataStoreManager)
	}

	@Test
	fun `authLogin should return success resource`() {
		// Arrange
		val requestBody = AuthLoginRequestBody("username", "password")
		val expectedResponse = AuthLoginRemoteResponse("", true)
		coEvery { repository.authLogin(requestBody) } returns Resource.Success(expectedResponse)

		// Act
		viewModel.authLogin(requestBody)

		// Assert
		val observer = mockk<Observer<Resource<AuthLoginRemoteResponse>>>(relaxed = true)
		viewModel.authLoginResult.observeForever(observer)

		// Since we're using InstantTaskExecutorRule, LiveData updates instantly
		assertEquals("Returned resource should be success", Resource.Success(expectedResponse), viewModel.authLoginResult.value)

		verify { observer.onChanged(Resource.Loading()) }
	}

	@Test
	fun `authLogin should return error resource`() {
		// Arrange
		val requestBody = AuthLoginRequestBody("username", "password")
		val exception = Exception("Login failed")
		coEvery { repository.authLogin(requestBody) } returns Resource.Error(exception)

		// Act
		viewModel.authLogin(requestBody)

		// Assert
		val observer = mockk<Observer<Resource<AuthLoginRemoteResponse>>>(relaxed = true)
		viewModel.authLoginResult.observeForever(observer)

		// Since we're using InstantTaskExecutorRule, LiveData updates instantly
		assertEquals("Returned resource should be error", Resource.Error<AuthLoginRemoteResponse>(exception), viewModel.authLoginResult.value)

		verify { observer.onChanged(Resource.Loading()) }
	}
}
