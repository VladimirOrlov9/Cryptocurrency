package com.vladimirorlov9.cryptocurrency.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladimirorlov9.cryptocurrency.domain.models.*
import com.vladimirorlov9.cryptocurrency.domain.usecase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CurrenciesViewModel(
    private val getSpecStatusUseCase: GetSpecStatusUseCase,
    private val finishOnboardingUseCase: FinishOnboardingUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val loadAllCoins: LoadAllCoinsUseCase
): ViewModel() {

    private val _resultLiveData = MutableLiveData<String>()
    val resultLiveData: LiveData<String> = _resultLiveData

    private val _specStatusLD = MutableLiveData<Boolean>()
    val specStatusLD: LiveData<Boolean> = _specStatusLD

    private val _signUpResultLD = MutableLiveData<Long?>()
    val signUpResultLD: LiveData<Long?> = _signUpResultLD

    private val _stockTokensLD = MutableLiveData<List<CoinInStock>>()
    val stockTokensLD: LiveData<List<CoinInStock>> = _stockTokensLD

    private val _stockNFTsLD = MutableLiveData<List<NFTsInStock>>()
    val stockNFTsLD: LiveData<List<NFTsInStock>> = _stockNFTsLD

    private val _allCoinsLD = MutableLiveData<List<SearchCoin>>()
    val allCoinsLD: LiveData<List<SearchCoin>> = _allCoinsLD

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

    fun getStockCoinsStatus(userId: Long) {
        // TODO after table creation add this block
    }

    fun getStockNFTsStatus(userId: Long) {
        // TODO after table creation add this block
    }

    fun loadAllCoins() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = loadAllCoins.execute()

            launch(Dispatchers.Main) {
                _allCoinsLD.value = result
            }
        }
    }

}