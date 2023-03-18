package com.vladimirorlov9.cryptocurrency.di

import com.vladimirorlov9.cryptocurrency.domain.usecase.*
import org.koin.dsl.module

val domainModule = module {

    factory<GetSpecStatusUseCase> {
        GetSpecStatusUseCase(specDao = get())
    }

    factory<FinishOnboardingUseCase> {
        FinishOnboardingUseCase(specDao = get())
    }

    factory<SignUpUseCase> {
        SignUpUseCase(userRepository = get())
    }

    factory<LoadAllCoinsUseCase> {
        LoadAllCoinsUseCase(
            currenciesRepository = get()
        )
    }

    factory<LoadCoinInfoUseCase> {
        LoadCoinInfoUseCase(
            currenciesRepository = get(),
            coinRepository = get()
        )
    }

    factory<LoadHistoricalCoinDataUseCase> {
        LoadHistoricalCoinDataUseCase(
            currenciesRepository = get()
        )
    }

    factory<GetBalanceInfoUseCase> {
        GetBalanceInfoUseCase(
            userRepository = get()
        )
    }

    factory<BuyCoinUseCase> {
        BuyCoinUseCase(
            coinRepository = get()
        )
    }

    factory<LoadStockTokensUseCase> {
        LoadStockTokensUseCase(
            coinRepository = get()
        )
    }

    factory<GetUserOverviewUseCase> {
        GetUserOverviewUseCase(
            userRepository = get()
        )
    }

    factory<GetUserFullInfoUseCase> {
        GetUserFullInfoUseCase(
            userRepository = get()
        )
    }

    factory<UpdateUserPictureUseCase> {
        UpdateUserPictureUseCase(
            userRepository = get()
        )
    }

    factory<DeleteProfilePictureUseCase> {
        DeleteProfilePictureUseCase(
            userRepository = get()
        )
    }
}