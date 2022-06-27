package com.example.proj3

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.proj3.databinding.FragmentRecycleViewNewsBinding


class RecycleViewNewsFragment : Fragment() {
    private lateinit var newsAdapter: RecyclerViewNewsAdapter
    private var viewBinding: FragmentRecycleViewNewsBinding? = null
    private lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recycle_view_news, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
        newsAdapter = RecyclerViewNewsAdapter(::onRecyclerViewItemClick)
        viewBinding = FragmentRecycleViewNewsBinding.bind(view)
        viewBinding?.apply {
            recyclerViewNewsRecyclerView.adapter = newsAdapter

            if (resources.configuration.orientation != Configuration.ORIENTATION_PORTRAIT)
            {
            recyclerViewNewsRecyclerView.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            } else
            {
                recyclerViewNewsRecyclerView.layoutManager = StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL)
            }

            recyclerViewNewsRecyclerView.addItemDecoration(
                DividerItemDecoration(
                    context,
                LinearLayoutManager.VERTICAL
                ).apply {
                    setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.shape_recycler_view_divider)!!)
                }
            )
        }
        viewModel.loadNews(childFragmentManager)
    }

    private fun bindViewModel() {
        viewModel = NewsViewModel()
        viewModel.news.observe(viewLifecycleOwner, ::updateNews)
        viewModel.newsIsLoading.observe(viewLifecycleOwner, ::updateLoadingState)
    }

    private fun onRecyclerViewItemClick(position: Int){
        (activity as MainActivity).showFragment(ViewPagerArticleFragment.newInstance(viewModel.news.value?.get(position)!!))
    }




    private fun updateNews(news: List<Article>){
        newsAdapter?.items = news
    }

    private fun updateLoadingState(isLoading: Boolean){

    }


    override fun onDestroy() {
        viewBinding = null
        super.onDestroy()
    }

    companion object {
        @JvmStatic
        fun newInstance() = RecycleViewNewsFragment()
    }
}