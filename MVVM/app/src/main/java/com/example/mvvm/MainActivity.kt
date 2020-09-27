package com.example.mvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.example.mvvm.fragments.AnimeListFragment

class MainActivity : AppCompatActivity() {
    private val animeListFragment = AnimeListFragment()
    private val fragmentManager: FragmentManager = supportFragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setFragment()
    }

    private fun setFragment(){
        fragmentManager.beginTransaction().replace(R.id.container, animeListFragment).commit()
    }
}