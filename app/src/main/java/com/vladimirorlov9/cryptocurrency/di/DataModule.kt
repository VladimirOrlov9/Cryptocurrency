package com.vladimirorlov9.cryptocurrency.di

import androidx.room.Room
import com.vladimirorlov9.cryptocurrency.data.api.CoinMarketCapApi
import com.vladimirorlov9.cryptocurrency.data.api.CoinPaprikaApi
import com.vladimirorlov9.cryptocurrency.data.api.CurrenciesApiInterface
import com.vladimirorlov9.cryptocurrency.data.api.retrofit2.CoinMarketCapService
import com.vladimirorlov9.cryptocurrency.data.api.retrofit2.CoinPaprikaApiService
import com.vladimirorlov9.cryptocurrency.data.repository.CurrenciesRepositoryImpl
import com.vladimirorlov9.cryptocurrency.data.storage.AppDatabase
import com.vladimirorlov9.cryptocurrency.domain.repository.CoinRepository
import com.vladimirorlov9.cryptocurrency.domain.repository.CurrenciesRepository
import com.vladimirorlov9.cryptocurrency.domain.repository.SpecificationsRepository
import com.vladimirorlov9.cryptocurrency.domain.repository.UserRepository
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {

    single<AppDatabase> {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "database"
        ).fallbackToDestructiveMigration()
            .build()
    }

    single<SpecificationsRepository> {
        val database = get<AppDatabase>()
        database.specificationsDao()
    }

    single<UserRepository> {
        val database = get<AppDatabase>()
        database.usersDao()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(CoinPaprikaApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<CoinPaprikaApiService> {
        val retrofit = get<Retrofit>()
        retrofit.create(CoinPaprikaApiService::class.java)
    }

    single<CurrenciesApiInterface> {
        CoinPaprikaApi(
            apiService = get()
        )
    }

    single<CurrenciesRepository> {
        CurrenciesRepositoryImpl(currenciesApiInterface = get())
    }

    single<CoinRepository> {
        val database = get<AppDatabase>()
        database.coinsDao()
    }
}