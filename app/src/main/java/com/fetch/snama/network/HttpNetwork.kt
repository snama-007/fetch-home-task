package com.fetch.snama.network

import com.fetch.snama.api.ItemRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * A singleton class responsible for creating and providing an instance of the [ItemRepository].
 */
object HttpNetwork {
    private const val BASE_URL = "https://fetch-hiring.s3.amazonaws.com/"

    fun getApiService(): ItemRepository {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ItemRepository::class.java)
    }
}
