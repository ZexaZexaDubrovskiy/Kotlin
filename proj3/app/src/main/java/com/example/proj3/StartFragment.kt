package com.example.proj3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import kotlin.concurrent.thread


class StartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val animateButton =
            AnimationUtils.loadAnimation(requireContext(), R.anim.anim_start_buttons)
        val RecyclerStartPageButton = view.findViewById<Button>(R.id.RecyclerStartPageButton)
        val ViewPagerStartPageButton = view.findViewById<Button>(R.id.ViewPagerStartPageButton)
        val knopka3StartPageButton = view.findViewById<Button>(R.id.knopka3StartPageButton)
        RecyclerStartPageButton.startAnimation(animateButton)
        ViewPagerStartPageButton.startAnimation(animateButton)
        knopka3StartPageButton.startAnimation(animateButton)


        RecyclerStartPageButton.setOnClickListener(onClick)
        ViewPagerStartPageButton.setOnClickListener(onClick2)
    }

    private var cancelFlag = false
    private val onClick = object : View.OnClickListener {
        override fun onClick(p0: View?) {

//            val btn = p0 as Button
//            btn.animate().apply {
//                startDelay = 1
//                duration = 300
////                translationYBy(500f)
////                translationXBy(-500f)
////                rotationYBy(10800f)
//            }
//            }
            (activity as MainActivity).showFragment(RecycleViewNewsFragment.newInstance())


        }
    }

    private val onClick2 = object : View.OnClickListener {
        override fun onClick(p0: View?) {
            (activity as MainActivity).showFragment(ViewPagerNewsFragment.newInstance())
        }

    }


    companion object {
        @JvmStatic
        fun newInstance() = StartFragment()
    }
}