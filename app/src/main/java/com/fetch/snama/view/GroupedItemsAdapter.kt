package com.fetch.snama.api

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.VisibleForTesting
import androidx.recyclerview.widget.RecyclerView
import com.fetch.snama.R
import com.fetch.snama.data.ItemEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * [RecyclerView.Adapter] that can display a [ItemEntity].
 * Handles grouping and sorting of items based on their listId.
 */
class GroupedItemsAdapter(private val isGrouped: Boolean) : RecyclerView.Adapter<GroupedItemsAdapter.ViewHolder>() {

    private var groupedItems: MutableList<Pair<Int, List<ItemEntity>>> = mutableListOf()
    private var items: List<ItemEntity> = emptyList()
    private val adapterScope = CoroutineScope(Dispatchers.Default + Job())
    fun setItems(items: List<ItemEntity>) {
        adapterScope.launch {
            this@GroupedItemsAdapter.items = items
            val sortedAndGroupedItems = items
                .filter { !it.name.isNullOrBlank() }
                .groupBy { it.listId }
                .toList()
                .sortedBy { it.first }

            withContext(Dispatchers.Main) {
                groupedItems.clear()
                sortedAndGroupedItems.forEachIndexed { index, item ->
                    groupedItems.add(item)
                    notifyItemInserted(index)
                }
            }
        }
    }

    fun getItems(): List<ItemEntity> {
        return items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.data_recycler_view_item, parent, false)
        return ViewHolder(view, isGrouped)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (listId, items) = groupedItems[position]
        holder.bind(listId, items)
    }

    override fun getItemCount(): Int = groupedItems.size

    class ViewHolder(view: View, private val isGrouped: Boolean) : RecyclerView.ViewHolder(view) {
        @VisibleForTesting
        val listIdTextView: TextView = view.findViewById(R.id.listIdTextView)
        @VisibleForTesting
        val itemsTextView: TextView = view.findViewById(R.id.itemsTextView)

        fun bind(listId: Int, items: List<ItemEntity>) {
            listIdTextView.text = itemView.context.getString(R.string.list_id).plus("$listId")
            if (isGrouped) {
                val itemsText = items.joinToString(", ") { "${it.id}: ${it.name}" }
                itemsTextView.text = itemsText
            }
            else {
                itemsTextView.text = items.joinToString("\n") { "${it.id}: ${it.name}" }
            }
        }
    }

    fun onCleared() {
        adapterScope.cancel()
    }
}