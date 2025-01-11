package com.fetch.snama.data
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents an item in the list.
 */
@Entity(tableName = "items")
data class ItemEntity(
    @PrimaryKey val id: Int,
    val listId: Int,
    val name: String?
)
