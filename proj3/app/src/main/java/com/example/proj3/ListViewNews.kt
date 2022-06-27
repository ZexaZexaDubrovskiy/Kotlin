package com.example.proj3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.proj3.databinding.FragmentListViewNewsBinding
import okhttp3.Call

class ListViewNews : Fragment() {
    private var viewBinding: FragmentListViewNewsBinding? = null


    //start
    private val visibleItem: MutableList<Article> = mutableListOf(
      //  Article(news) //chto???
    )
    private lateinit var newsAdapter: NewsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsAdapter = NewsAdapter(requireContext(), R.layout.item_news, visibleItem)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding = FragmentListViewNewsBinding.bind(view).apply {
            listViewNewsListView.adapter = newsAdapter
            newsAdapter.notifyDataSetChanged()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_view_news, container, false)
    }

    override fun onDestroyView() {
        viewBinding = null
        super.onDestroyView()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ListViewNews()
    }
}