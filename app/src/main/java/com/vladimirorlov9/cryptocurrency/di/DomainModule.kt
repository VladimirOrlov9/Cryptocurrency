package com.vladimirorlov9.cryptocurrency.di

import com.vladimirorlov9.cryptocurrency.domain.usecase.GetLatestCryptoStatusUseCase
import com.vladimirorlov9.cryptocurrency.domain.usecase.GetUserNameUseCase
import com.vladimirorlov9.cryptocurrency.domain.usecase.SaveUserNameUseCase
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
}