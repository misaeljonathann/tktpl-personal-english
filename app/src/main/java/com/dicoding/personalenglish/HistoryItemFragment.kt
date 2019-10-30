package com.dicoding.personalenglish

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.personalenglish.data.HistoryDate
import com.dicoding.personalenglish.data.HistoryItem
import com.dicoding.personalenglish.data.HistoryWord
import com.dicoding.personalenglish.data.Word
import com.dicoding.personalenglish.databinding.FragmentHistoryItemBinding
import com.dicoding.personalenglish.ui.WordViewModel
import com.dicoding.personalenglish.util.InjectorUtils
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.sql.Date
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class HistoryItemFragment: Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var mView: View
    private lateinit var wordViewModel: WordViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentHistoryItemBinding = FragmentHistoryItemBinding.inflate(inflater, container, false)
        this.mView = binding.root
        this.recyclerView = mView.findViewById(R.id.rv_history)


        showRecyclerList()
        return mView
    }

    private fun showRecyclerList() {

        recyclerView.setLayoutManager(LinearLayoutManager(mView.context))
        recyclerView.setHasFixedSize(true)

        val adapter = HistoryAdapter()
        recyclerView.setAdapter(adapter)

        val factory = InjectorUtils.provideQuotesViewModelFactory(mView.context)

        wordViewModel = ViewModelProviders.of(this, factory)
            .get(WordViewModel::class.java)
        wordViewModel.getAllWords().observe(this, Observer { words ->
            var historyItem = groupDataIntoHashMap(words)
            adapter.setWords(historyItem)
        })
    }

    fun groupDataIntoHashMap(words: List<Word>): MutableList<HistoryItem> {

        var musedList = mutableListOf<HistoryItem>()
        var currentDate: String? = null

        for (word in words.reversed()) {
            if (currentDate != word.time.toString()) {
                currentDate = word.time.toString()
                var historyDate = HistoryDate(currentDate)
                musedList.add(historyDate)
            }
            var historyWord = HistoryWord(word.word)
            musedList.add(historyWord)
        }
        return musedList
    }
}