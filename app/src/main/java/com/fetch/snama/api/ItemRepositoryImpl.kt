package com.fetch.snama.api

import com.fetch.snama.data.ItemEntity
import com.fetch.snama.network.HttpNetwork

class ItemRepositoryImpl: ItemRepository {
    private val api =  HttpNetwork.getApiService() //retrofit.create(ItemRepository::class.java)

    /**
     * Fetches a list of [ItemEntity] objects from the API.
     *
     * @return A list of [ItemEntity] objects retrieved from the API.
     */
    override suspend fun fetchItems(): List<ItemEntity> {
        return api.fetchItems()
    }
}