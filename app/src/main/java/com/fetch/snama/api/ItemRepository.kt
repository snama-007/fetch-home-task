package com.fetch.snama.api

import com.fetch.snama.data.ItemEntity
import retrofit2.http.GET

/**
 * Provides an interface for fetching a list of [ItemEntity] objects from the server.
 */
interface ItemRepository {
    @GET("hiring.json")
    suspend fun fetchItems(): List<ItemEntity>
}