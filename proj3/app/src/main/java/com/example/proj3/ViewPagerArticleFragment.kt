package com.example.proj3

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.proj3.databinding.FragmentViewPagerArticleBinding

class ViewPagerArticleFragment : Fragment() {
    private var viewBinding: FragmentViewPagerArticleBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_view_pager_article, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val article = arguments?.getSerializable(KEY_ARTICLE) as Article
        viewBinding = FragmentViewPagerArticleBinding.bind(view).apply {
            viewPagerArticleTitleTextView.text = article.title
            viewPagerArticleAnnotationTextView.text = article.annotation
            var text = article.text
            text = text.replace("&nbsp;", " ")
            text = text.replace("  ", " ")
            text = text.replace(" \n", "\n")
            text = text.replace("\n\n\n", "\n\n")

            viewPagerArticleContentTextView.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)

            Glide
                .with(requireContext())
                .load(article.imageUrl)
                .apply(RequestOptions()
                    .centerCrop()
                    .fitCenter())
                .into(viewPageArticleImageView);
        }
    }

    override fun onDestroyView() {
        viewBinding = null
        super.onDestroyView()
    }

    companion object {
        private const val KEY_ARTICLE = "KEY_ARTICLE"

        @JvmStatic
        fun newInstance(article: Article) =
            ViewPagerArticleFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_ARTICLE, article)
                }
            }
    }
}