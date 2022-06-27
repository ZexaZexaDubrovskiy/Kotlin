package com.example.rpcollegemobile.itemEvent

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.rpcollegemobile.R
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class EventAdapterDelegate(
    val onItemClick: (position: Int) -> Unit
) :
    AbsListItemAdapterDelegate<Event, Event, EventHolder>() {

    override fun isForViewType(item: Event, items: MutableList<Event>, position: Int): Boolean {
        return item is Event
    }

    override fun onCreateViewHolder(parent: ViewGroup): EventHolder {
        return EventHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_event, parent, false),
            onItemClick
        )
    }

    override fun onBindViewHolder(
        item: Event,
        holder: EventHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }
}