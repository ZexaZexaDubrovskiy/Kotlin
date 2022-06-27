package com.example.proj3

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class RecyclerViewArticleAdapterDelegate(
    val onItemClick: (position: Int) -> Unit
) :
    AbsListItemAdapterDelegate<Article, Article, RecyclerViewArticleHolder>() {

    override fun isForViewType(item: Article, items: MutableList<Article>, position: Int): Boolean {
        return item is Article
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerViewArticleHolder {
        return  RecyclerViewArticleHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_recycler_view_article, parent, false),
            onItemClick
        )
    }

    override fun onBindViewHolder(
        item: Article,
        holder: RecyclerViewArticleHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }
}