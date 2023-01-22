package com.vladimirorlov9.cryptocurrency.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladimirorlov9.cryptocurrency.domain.models.CurrenciesStatus
import com.vladimirorlov9.cryptocurrency.domain.models.UserName
import com.vladimirorlov9.cryptocurrency.domain.usecase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CurrenciesViewModel(
    private val getLatestCryptoStatusUseCase: GetLatestCryptoStatusUseCase,
    private val getSpecStatusUseCase: GetSpecStatusUseCase,
    private val finishOnboardingUseCase: FinishOnboardingUseCase
): ViewModel() {

    init {
        loadLatestCurrencies()
    }

    private val _resultLiveData = MutableLiveData<String>()
    val resultLiveData: LiveData<String> = _resultLiveData

    private val _specStatusLD = MutableLiveData<Boolean>()
    val specStatusLD: LiveData<Boolean> = _specStatusLD

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

}