package com.example.proj3

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.viewpager2.widget.ViewPager2
import com.example.proj3.databinding.FragmentViewPagerNewsBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.math.abs

class ViewPagerNewsFragment : Fragment() {
    private var viewBinding: FragmentViewPagerNewsBinding? = null
    private var newsAdapter: ViewPagerNewsAdapter? = null
    private val newsViewModel: NewsViewModel = NewsViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_view_pager_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsAdapter = ViewPagerNewsAdapter(this@ViewPagerNewsFragment)
        bindViewModel()
        newsViewModel.loadNews(childFragmentManager)
        viewBinding = FragmentViewPagerNewsBinding.bind(view)

        viewBinding?.apply {
            viewPagerNewsToolbar.setOnMenuItemClickListener(onToolbarMenuItemClickListener)
            viewPagerNewsViewPager.offscreenPageLimit = 1
            viewPagerNewsViewPager.setPageTransformer(transformer)
            viewPagerNewsViewPager.adapter = newsAdapter
        }

        childFragmentManager.setFragmentResultListener(
            AlertDialogFragment.REQUEST_KEY_ALERT_DIALOG,
            viewLifecycleOwner,
            onFragmentResultListener
        )
    }

    private fun bindViewModel() {
        newsViewModel.news.observe(viewLifecycleOwner) { news ->
            newsAdapter?.news = news
            viewBinding?.apply {
                newsAdapter?.notifyItemRangeChanged(0, newsAdapter!!.itemCount)
                viewPagerNewsDotsIndicator.setViewPager2(viewPagerNewsViewPager)
                TabLayoutMediator(
                    viewPagerNewsTabLayout,
                    viewPagerNewsViewPager
                ) { tab, position -> tab.text = news[position].header }.attach()
            }
        }



        newsViewModel.newsIsLoading.observe(viewLifecycleOwner) { isLoading ->
            viewBinding?.apply {
                if (isLoading) viewPagerNewsProgressBar.visibility = View.VISIBLE else viewPagerNewsProgressBar.visibility = View.INVISIBLE
            }
        }
    }

    //alertdialog



    private val onFragmentResultListener = object : FragmentResultListener {
        override fun onFragmentResult(requestKey: String, result: Bundle) {
            when (requestKey) {
                AlertDialogFragment.REQUEST_KEY_ALERT_DIALOG -> {
                    //TO-DO FOR FEATURE MB?
                }
            }
        }
    }

    private val transformer = object : ViewPager2.PageTransformer {
        private val MIN_SCALE = 0.85f
        private val MIN_ALPHA = 0.5f
        override fun transformPage(page: View, position: Float) {
            val pageWidth = page.width
            val pageHeight = page.height

            when {
                position < -1 -> {
                    page.alpha = 0f
                }
                position <= 1 -> {
                    val scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position))
                    val vertMargin = pageHeight * (1 - scaleFactor) / 2
                    val horzMargin = pageWidth * (1 - scaleFactor) / 2
                    if (position < 0) {
                        page.translationX = horzMargin - vertMargin / 2
                    } else {
                        page.translationX = -horzMargin + vertMargin / 2
                    }

                    page.scaleX = scaleFactor
                    page.scaleY = scaleFactor

                    page.alpha = MIN_ALPHA +
                            (scaleFactor - MIN_SCALE) /
                            (1 - MIN_SCALE) * (1 - MIN_ALPHA)
                }
                else -> {
                    page.alpha = 0f
                }
            }


//            page.cameraDistance = 20000f
//            when {
//                position < -1 -> {
//                    page.alpha = 0f
//                }
//                position <= 0 -> {
//                    page.alpha = 1f
//                    page.pivotX = page.width.toFloat()
//                    page.rotationY = 90 * abs(position)
//                }
//                position <= 1 -> {
//                    page.alpha = 1f
//                    page.pivotX = 0f
//                    page.rotationY = -90 * abs(position)
//                }
//                else -> {
//                    page.alpha = 0f
//                }
//            }
//
//            if (abs(position) <= 0.5) {
//                page.scaleY = .4f.coerceAtLeast(1 - abs(position))
//            } else if (abs(position) <= 1) {
//                page.scaleY = .4f.coerceAtLeast(1 - abs(position))
//            }
        }
    }

    private val onToolbarMenuItemClickListener = object : Toolbar.OnMenuItemClickListener {
        override fun onMenuItemClick(item: MenuItem?): Boolean {
            AlertDialogFragment.newInstance(
                "About",
                "Text About This App",
                "Ponyatno",
                ""
            ).show(childFragmentManager, AlertDialogFragment.REQUEST_KEY_ALERT_DIALOG)
            return true
        }
    }

    override fun onDestroyView() {
        viewBinding = null
        super.onDestroyView()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ViewPagerNewsFragment()
    }
}