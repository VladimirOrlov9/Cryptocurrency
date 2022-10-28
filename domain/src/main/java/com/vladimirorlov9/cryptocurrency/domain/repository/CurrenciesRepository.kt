package com.vladimirorlov9.cryptocurrency.domain.repository

import com.vladimirorlov9.cryptocurrency.domain.models.CurrenciesStatus

interface CurrenciesRepository {

    suspend fun getStatus(): CurrenciesStatus
}