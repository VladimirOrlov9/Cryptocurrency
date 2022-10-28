package com.vladimirorlov9.cryptocurrency.ui.currenciesfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladimirorlov9.cryptocurrency.domain.models.CurrenciesStatus
import com.vladimirorlov9.cryptocurrency.domain.usecase.GetLatestCryptoStatusUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CurrenciesViewModel(
    private val getLatestCryptoStatusUseCase: GetLatestCryptoStatusUseCase
): ViewModel() {

    init {
        loadLatestCurrencies()
    }

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
}