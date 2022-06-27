package com.example.proj3

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class RecyclerViewNewsAdapter(
    onItemClick: (position: Int) -> Unit
): AsyncListDifferDelegationAdapter<Article>(DifferCallback()) {


    init {
        delegatesManager.addDelegate(RecyclerViewArticleAdapterDelegate(onItemClick))
    }

    class DifferCallback: DiffUtil.ItemCallback<Article>() {


        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return newItem.id == oldItem.id
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return newItem.equals(oldItem)
        }



    }
}