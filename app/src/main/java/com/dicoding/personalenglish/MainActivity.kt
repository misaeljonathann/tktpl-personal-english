package com.dicoding.personalenglish

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.personalenglish.ui.WordViewModel
import com.dicoding.personalenglish.util.InjectorUtils

class MainActivity : AppCompatActivity() {
    private lateinit var wordViewModel: WordViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var actionBar = getSupportActionBar()
        actionBar?.setCustomView(R.layout.actionbar_layout)
    }
}