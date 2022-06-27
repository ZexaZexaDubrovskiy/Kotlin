package com.example.rpcollegemobile.itemEvent

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter


class EventAdapter(
    onItemClick: (position: Int) -> Unit
): AsyncListDifferDelegationAdapter<Event>(DifferCallback()) {

    init {
        delegatesManager.addDelegate(EventAdapterDelegate(onItemClick))
    }

    class DifferCallback: DiffUtil.ItemCallback<Event>() {

        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
            return newItem.id == oldItem.id
        }

        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
            return newItem.equals(oldItem)
        }
    }
}