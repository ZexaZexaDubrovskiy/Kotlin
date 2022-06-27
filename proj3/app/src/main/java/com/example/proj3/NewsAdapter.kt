package com.example.proj3

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class NewsAdapter(
    private val context: Context,
    private val resource: Int,
    private val objects: MutableList<Article>
) : BaseAdapter() {
    override fun getCount(): Int {
        return objects.size
    }

    override fun getItem(pos: Int): Any {
        return objects[pos]
    }

    override fun getItemId(pos: Int): Long {
        return pos.toLong()
    }

    override fun getView(pos: Int, view: View?, parent: ViewGroup?): View {
        val item = getItem(pos) as Article
        val holder = view ?: LayoutInflater.from(context).inflate(resource, parent, false)

        val itemTitle = holder.findViewById<TextView>(R.id.l_item_title)
        itemTitle.text = item.title

        val itemText = holder.findViewById<TextView>(R.id.l_item_text)
        itemText.text = item.text

        Glide
            .with(holder)
            .load(item.imageUrl)
            .apply(
                RequestOptions()
                .centerCrop()
                .fitCenter())
            .into(holder.findViewById(R.id.listViewImageView));

        return holder
    }
}