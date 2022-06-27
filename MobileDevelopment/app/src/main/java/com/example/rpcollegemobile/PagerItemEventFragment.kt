package com.example.rpcollegemobile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.rpcollegemobile.databinding.FragmentPagerItemEventBinding
import com.example.rpcollegemobile.itemEvent.Event

class PagerItemEventFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pager_item_event, container, false)
    }

    private var viewBinding: FragmentPagerItemEventBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val event = arguments?.getSerializable(KEY_EVENT) as Event
        viewBinding = FragmentPagerItemEventBinding.bind(view).apply {
            mainFragmentToolbar.title = event.activityType


            nameTextView.text = event.name

            if (event.planDate != "null") {
                //не бейте, пожалуйста
                var date = event.planDate.split('-')
                dataAndTimeTextView.text = date[2]+"."+date[1] +"." +date[0] + " " + event.planStartTime + "-" + event.planEndTime
            }

        }

    }


    override fun onDestroy() {
        viewBinding = null
        super.onDestroy()
    }

    companion object {
        private const val KEY_EVENT = "KEY_EVENT"
        @JvmStatic
        fun newInstance(event: Event) =
            PagerItemEventFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_EVENT, event)
                }
            }
    }
}