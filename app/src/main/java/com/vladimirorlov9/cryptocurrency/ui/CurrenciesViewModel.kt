package com.vladimirorlov9.cryptocurrency.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladimirorlov9.cryptocurrency.domain.models.UserFullInfo
import com.vladimirorlov9.cryptocurrency.domain.models.*
import com.vladimirorlov9.cryptocurrency.domain.usecase.*
import com.vladimirorlov9.cryptocurrency.utils.models.CoinInfoForBuy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CurrenciesViewModel(
    private val getSpecStatusUseCase: GetSpecStatusUseCase,
    private val finishOnboardingUseCase: FinishOnboardingUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val loadAllCoins: LoadAllCoinsUseCase,
    private val loadCoinInfoUseCase: LoadCoinInfoUseCase,
    private val loadHistoricalCoinDataUseCase: LoadHistoricalCoinDataUseCase,
    private val getBalanceInfoUseCase: GetBalanceInfoUseCase,
    private val buyCoinUseCase: BuyCoinUseCase,
    private val loadStockTokensUseCase: LoadStockTokensUseCase,
    private val getUserOverviewUseCase: GetUserOverviewUseCase,
    private val getUserFullInfoUseCase: GetUserFullInfoUseCase
) : ViewModel() {

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

    private val _coinInfoLD = MutableLiveData<CoinScreenModel?>()
    val coinInfoLD: LiveData<CoinScreenModel?> = _coinInfoLD

    private val _coinHistoryLD = MutableLiveData<List<CoinHistoryModel>>()
    val coinHistoryLD: LiveData<List<CoinHistoryModel>> = _coinHistoryLD

    private val _balanceInfoLD = MutableLiveData<BalanceInfo>()
    val balanceInfoLD: LiveData<BalanceInfo> = _balanceInfoLD

    private val _coinInfoForBuyLD = MutableLiveData<CoinInfoForBuy>()
    val coinInfoForBuyLD: LiveData<CoinInfoForBuy> = _coinInfoForBuyLD


    private val _historyMap: MutableMap<Int, List<CoinHistoryModel>> = mutableMapOf()

    private val _paymentSuccessfulLD = MutableLiveData(false)
    val paymentSuccessfulLD: LiveData<Boolean> = _paymentSuccessfulLD

    private val _userOverviewLD = MutableLiveData<UserOverviewModel>()
    val userOverviewLD: LiveData<UserOverviewModel> = _userOverviewLD

    private val _userFullInfoLD = MutableLiveData<UserFullInfo>()
    val userFullInfoLD: LiveData<UserFullInfo> = _userFullInfoLD

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

    fun getStockCoinsStatus(userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val tokens = loadStockTokensUseCase.execute(userId)

            withContext(Dispatchers.Main) {
                _stockTokensLD.value = tokens
            }
        }
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

    fun loadCoinInfo(coinId: String, userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = loadCoinInfoUseCase.execute(coinId, userId)

            launch(Dispatchers.Main) {
                _coinInfoLD.value = result

                _coinInfoForBuyLD.value = CoinInfoForBuy(
                    id = coinId,
                    name = result.coinInfo.name,
                    symbol = result.coinInfo.symbol,
                    logo = result.coinInfo.logo,
                    price = result.coinCourse
                )
            }
        }
    }

    fun loadHistoricalCoinData(coinId: String, days: Int) {
        if (_historyMap.containsKey(days))
            _coinHistoryLD.value = _historyMap[days]
        else {
            viewModelScope.launch(Dispatchers.IO) {
                val result = loadHistoricalCoinDataUseCase.execute(
                    coinId = coinId,
                    days = days
                )
                _historyMap[days] = result

                launch(Dispatchers.Main) {
                    _coinHistoryLD.value = _historyMap[days]
                }
            }
        }
    }

    fun loadBalanceInfo(userId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getBalanceInfoUseCase.execute(userId)

            launch(Dispatchers.Main) {
                _balanceInfoLD.value = result
            }
        }
    }

    fun buyCryptoByWallet(userId: Int, coin: BuyCoin, price: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            val newBalance = buyCoinUseCase.execute(
                userId = userId,
                buyCoin = coin,
                price = price
            )

            withContext(Dispatchers.Main) {
                _balanceInfoLD.value = newBalance
                _paymentSuccessfulLD.value = true
            }
        }

    }

    fun resetPaymentSuccessfulLD() {
        _paymentSuccessfulLD.postValue(false)
    }

    fun clearCoinInfo() {
        _coinInfoLD.value = null
        _coinHistoryLD.value = listOf()
    }

    fun loadUserOverview(userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getUserOverviewUseCase.execute(userId)

            withContext(Dispatchers.Main) {
                _userOverviewLD.value = result
            }
        }
    }

    fun loadUserFullInfo(uid: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getUserFullInfoUseCase.execute(uid = uid)
            _userFullInfoLD.postValue(result)
        }
    }

}