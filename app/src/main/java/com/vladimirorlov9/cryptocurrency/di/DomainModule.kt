package com.vladimirorlov9.cryptocurrency.di

import com.vladimirorlov9.cryptocurrency.domain.usecase.*
import org.koin.dsl.module

val domainModule = module {

    factory<GetLatestCryptoStatusUseCase> {
        GetLatestCryptoStatusUseCase(currenciesRepository = get())
    }

    factory<GetSpecStatusUseCase> {
        GetSpecStatusUseCase(specDao = get())
    }

    factory<FinishOnboardingUseCase> {
        FinishOnboardingUseCase(specDao = get())
    }

    factory<SignUpUseCase> {
        SignUpUseCase(userRepository = get())
    }
}