package com.example.proj3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
           // showFragment(ViewPagerNewsFragment.newInstance()) //отображение табами
            showFragment(StartFragment.newInstance()) //отображение листом
        }
    }

    fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, fragment)
            if (fragment !is StartFragment)
            {
                addToBackStack(null)
            }
            commit()
        }
    }
}