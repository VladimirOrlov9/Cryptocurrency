package com.vladimirorlov9.cryptocurrency.di

import androidx.room.Room
import com.vladimirorlov9.cryptocurrency.data.api.CoinMarketCapApi
import com.vladimirorlov9.cryptocurrency.data.api.CurrenciesApiInterface
import com.vladimirorlov9.cryptocurrency.data.repository.CurrenciesRepositoryImpl
import com.vladimirorlov9.cryptocurrency.data.storage.AppDatabase
import com.vladimirorlov9.cryptocurrency.domain.repository.CurrenciesRepository
import com.vladimirorlov9.cryptocurrency.domain.repository.SpecificationsRepository
import com.vladimirorlov9.cryptocurrency.domain.repository.UserRepository
import org.koin.dsl.module

val dataModule = module {

    single<CurrenciesApiInterface> {
        CoinMarketCapApi()
    }

    single<CurrenciesRepository> {
        CurrenciesRepositoryImpl(currenciesApiInterface = get())
    }

    single<AppDatabase> {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "database"
        ).build()
    }

    single<SpecificationsRepository> {
        val database = get<AppDatabase>()
        database.specificationsDao()
    }

    single<UserRepository> {
        val database = get<AppDatabase>()
        database.usersDao()
    }
}