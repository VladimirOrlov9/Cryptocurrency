package com.vladimirorlov9.cryptocurrency.di

import com.vladimirorlov9.cryptocurrency.data.api.CoinMarketCapApi
import com.vladimirorlov9.cryptocurrency.data.api.CurrenciesApiInterface
import com.vladimirorlov9.cryptocurrency.data.repository.CurrenciesRepositoryImpl
import com.vladimirorlov9.cryptocurrency.data.repository.UserRepositoryImpl
import com.vladimirorlov9.cryptocurrency.data.storage.SharedPrefUserStorage
import com.vladimirorlov9.cryptocurrency.data.storage.UserStorageInterface
import com.vladimirorlov9.cryptocurrency.domain.repository.CurrenciesRepository
import com.vladimirorlov9.cryptocurrency.domain.repository.UserRepository
import org.koin.dsl.module

val dataModule = module {

    single<UserStorageInterface> {
        SharedPrefUserStorage(context = get())
    }

    single<UserRepository> {
        UserRepositoryImpl(userStorageInterface = get())
    }

    single<CurrenciesApiInterface> {
        CoinMarketCapApi()
    }

    single<CurrenciesRepository> {
        CurrenciesRepositoryImpl(currenciesApiInterface = get())
    }
}