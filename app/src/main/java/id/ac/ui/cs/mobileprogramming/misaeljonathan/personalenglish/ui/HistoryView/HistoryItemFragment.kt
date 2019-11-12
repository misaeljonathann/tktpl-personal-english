package id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.ui.HistoryView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.databinding.FragmentHistoryItemBinding
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.ui.WordViewModel
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.R
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.data.HistoryItem
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.data.HistoryWord
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.data.HistoryDate
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.data.Word
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.util.InjectorUtils

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

        val factory = InjectorUtils.provideWordViewModelFactory(mView.context)

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