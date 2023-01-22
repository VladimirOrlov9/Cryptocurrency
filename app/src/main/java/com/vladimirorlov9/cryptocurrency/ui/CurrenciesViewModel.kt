package com.vladimirorlov9.cryptocurrency.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladimirorlov9.cryptocurrency.domain.models.CurrenciesStatus
import com.vladimirorlov9.cryptocurrency.domain.models.NewUser
import com.vladimirorlov9.cryptocurrency.domain.usecase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CurrenciesViewModel(
    private val getLatestCryptoStatusUseCase: GetLatestCryptoStatusUseCase,
    private val getSpecStatusUseCase: GetSpecStatusUseCase,
    private val finishOnboardingUseCase: FinishOnboardingUseCase,
    private val signUpUseCase: SignUpUseCase
): ViewModel() {

    init {
        loadLatestCurrencies()
    }

    private val _resultLiveData = MutableLiveData<String>()
    val resultLiveData: LiveData<String> = _resultLiveData

    private val _specStatusLD = MutableLiveData<Boolean>()
    val specStatusLD: LiveData<Boolean> = _specStatusLD

    private val _signUpResultLD = MutableLiveData<Long?>()
    val signUpResultLD: LiveData<Long?> = _signUpResultLD

    private val _latestCryptoLD = MutableLiveData<CurrenciesStatus>()
    val latestCryptoLD: LiveData<CurrenciesStatus> = _latestCryptoLD

    fun loadLatestCurrencies() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getLatestCryptoStatusUseCase.execute()

            launch(Dispatchers.Main) {
                _latestCryptoLD.value = result
            }
        }
    }

    fun getSpecStatus(specName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getSpecStatusUseCase.execute(specName)

            launch(Dispatchers.Main) {
                _specStatusLD.value = result
            }
        }
    }

    fun finishOnboarding(specName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            finishOnboardingUseCase.execute(specName)
        }
    }

    fun signUp(
        firstName: String,
        lastName: String,
        email: String,
        phone: String,
        password: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = signUpUseCase.execute(
                NewUser(
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    phoneNumber = phone,
                    password = password,
                    registrationDate = System.currentTimeMillis()
                )
            )

            launch(Dispatchers.Main) {
                _signUpResultLD.value = result
            }
        }
    }

}