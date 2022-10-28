package com.vladimirorlov9.cryptocurrency.data.api

import com.vladimirorlov9.cryptocurrency.data.api.models.LatestCryptoStatus

interface CurrenciesApiInterface {

    suspend fun getLatest(): LatestCryptoStatus
}