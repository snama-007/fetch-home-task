package com.fetch.snama

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fetch.snama.api.GroupedItemsAdapter
import com.fetch.snama.api.ItemRepositoryImpl
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var recyclerViewDataItems: RecyclerView
    private lateinit var adapter: GroupedItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_view)

        // Initialize RecyclerView and fetchButtonGroup/List
        recyclerViewDataItems = findViewById(R.id.recyclerViewDataItems)
        recyclerViewDataItems.layoutManager = LinearLayoutManager(this)

        // Initialize fetchButtonGroup/List
        val fetchButtonGroup = findViewById<Button>(R.id.fetchButtonGroup)
        val fetchButtonList = findViewById<Button>(R.id.fetchButtonList)

        fetchButtonGroup.setOnClickListener {
            fetchItems(true)
        }

        fetchButtonList.setOnClickListener {
            fetchItems(false)
        }
    }

    private fun fetchItems(group: Boolean) {
        // Update RecyclerView adapter and fetch data from the repository
        adapter = GroupedItemsAdapter(group)
        recyclerViewDataItems.adapter = adapter
        lifecycleScope.launch {
            // Fetch elements from the repository
            try {
                val repository = ItemRepositoryImpl()
                val items = repository.fetchItems()
                adapter.setItems(items)
            } catch (e: Exception) {
                Log.e("MainActivity", "Error fetching items", e)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter.onCleared()
    }
}