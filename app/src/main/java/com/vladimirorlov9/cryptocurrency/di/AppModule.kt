package com.vladimirorlov9.cryptocurrency.di

import com.vladimirorlov9.cryptocurrency.ui.currenciesfragment.CurrenciesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel<CurrenciesViewModel> {
        CurrenciesViewModel(
            getLatestCryptoStatusUseCase = get()
        )
    }
}