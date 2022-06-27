package com.example.proj3

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerNewsAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {

    var news: List<Article> = listOf()

    override fun getItemCount(): Int {
        return news.size
    }

    override fun createFragment(position: Int): Fragment {
        return ViewPagerArticleFragment.newInstance(news[position])
    }
}