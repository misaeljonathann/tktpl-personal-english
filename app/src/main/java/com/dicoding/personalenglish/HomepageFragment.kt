package com.dicoding.personalenglish

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dicoding.personalenglish.databinding.FragmentHomepageBinding

class HomepageFragment: Fragment() {
    private lateinit var mView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentHomepageBinding = FragmentHomepageBinding.inflate(inflater, container, false)
        this.mView = binding.root

        return mView
    }
}