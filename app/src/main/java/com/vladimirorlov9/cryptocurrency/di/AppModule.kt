package com.vladimirorlov9.cryptocurrency.di

import com.vladimirorlov9.cryptocurrency.ui.CurrenciesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel<CurrenciesViewModel> {
        CurrenciesViewModel(
            getSpecStatusUseCase = get(),
            finishOnboardingUseCase = get(),
            signUpUseCase = get(),
            loadAllCoins = get(),
            loadCoinInfoUseCase = get(),
            loadHistoricalCoinDataUseCase = get(),
            getBalanceInfoUseCase = get(),
            buyCoinUseCase = get(),
            loadStockTokensUseCase = get()
        )
    }
}