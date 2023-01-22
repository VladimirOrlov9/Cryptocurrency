package com.vladimirorlov9.cryptocurrency.di

import com.vladimirorlov9.cryptocurrency.domain.usecase.*
import org.koin.dsl.module

val domainModule = module {

    factory<SaveUserNameUseCase> {
        SaveUserNameUseCase(userRepository = get())
    }

    factory<GetUserNameUseCase> {
        GetUserNameUseCase(userRepository = get())
    }

    factory<GetLatestCryptoStatusUseCase> {
        GetLatestCryptoStatusUseCase(currenciesRepository = get())
    }

    factory<GetSpecStatusUseCase> {
        GetSpecStatusUseCase(specDao = get())
    }

    factory<FinishOnboardingUseCase> {
        FinishOnboardingUseCase(specDao = get())
    }
}