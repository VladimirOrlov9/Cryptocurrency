package com.vladimirorlov9.cryptocurrency.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladimirorlov9.cryptocurrency.domain.models.CurrenciesStatus
import com.vladimirorlov9.cryptocurrency.domain.models.UserName
import com.vladimirorlov9.cryptocurrency.domain.usecase.GetLatestCryptoStatusUseCase
import com.vladimirorlov9.cryptocurrency.domain.usecase.GetUserNameUseCase
import com.vladimirorlov9.cryptocurrency.domain.usecase.SaveUserNameUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CurrenciesViewModel(
    private val getLatestCryptoStatusUseCase: GetLatestCryptoStatusUseCase
): ViewModel() {

    init {
        loadLatestCurrencies()
    }

    private val _resultLiveData = MutableLiveData<String>()
    val resultLiveData: LiveData<String> = _resultLiveData

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

//    fun saveText(text: String) {
//        val userNameParam = UserName(name = text)
//        val result = saveUserNameUseCase.execute(userNameParam)
//        _resultLiveData.value = "Success = $result"
//    }
//
//    fun loadText() {
//        val userName = getUserNameUseCase.execute()
//        _resultLiveData.value = "Result = ${userName.name}"
//    }
}