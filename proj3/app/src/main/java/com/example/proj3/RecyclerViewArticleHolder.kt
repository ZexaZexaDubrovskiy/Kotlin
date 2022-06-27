package com.example.proj3

import android.view.View
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proj3.databinding.ItemRecyclerViewArticleBinding

class RecyclerViewArticleHolder(
    view: View,
    onItemClick: (position: Int) -> Unit
): RecyclerView.ViewHolder(view) {
    init {
        view.setOnClickListener { onItemClick(this.layoutPosition) }
    }

    private val viewBinding = ItemRecyclerViewArticleBinding.bind(view)

    fun bind(article: Article){
        val tittleTextView = itemView.findViewById<TextView>(R.id.recyclerViewArticleTitleTextView)
         article.text
        viewBinding.apply {
            recyclerViewArticleTitleTextView.text = article.title
            recyclerViewArticleAnnotationTextView.text = article.annotation
            Glide.with(itemView.context)
                .load(article.imageUrl)
                .into(recyclerViewArticlePhotoImageView)
                //.errorPlaceHolder()
        }
    }

    //mechanism destroy

}