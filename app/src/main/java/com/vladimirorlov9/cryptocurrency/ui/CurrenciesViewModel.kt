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
    private val loadAllCoins: LoadAllCoinsUseCase,
    private val loadCoinInfoUseCase: LoadCoinInfoUseCase,
    private val loadHistoricalCoinDataUseCase: LoadHistoricalCoinDataUseCase
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

    private val _coinInfoLD = MutableLiveData<CoinInfoModel>()
    val coinInfoLD: LiveData<CoinInfoModel> = _coinInfoLD

    private val _coinHistoryLD = MutableLiveData<List<CoinHistoryModel>>()
    val coinHistoryLD: LiveData<List<CoinHistoryModel>> = _coinHistoryLD

    lateinit var latestCryptoPrice: CoinHistoryModel

    val historyMap: MutableMap<Int, List<CoinHistoryModel>> = mutableMapOf()

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

    fun loadCoinInfo(coinId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = loadCoinInfoUseCase.execute(coinId)

            launch(Dispatchers.Main) {
                _coinInfoLD.value = result
            }
        }
    }

    fun loadHistoricalCoinData(coinId: String, days: Int) {
        if (historyMap.containsKey(days))
            _coinHistoryLD.value = historyMap[days]
        else {
            viewModelScope.launch(Dispatchers.IO) {
                val result = loadHistoricalCoinDataUseCase.execute(coinId, days).toMutableList()
                if (!::latestCryptoPrice.isInitialized)
                    latestCryptoPrice = result.last()
                else
                    result.add(latestCryptoPrice)
                historyMap[days] = result

                launch(Dispatchers.Main) {
                    _coinHistoryLD.value = result
                }
            }
        }
    }

}