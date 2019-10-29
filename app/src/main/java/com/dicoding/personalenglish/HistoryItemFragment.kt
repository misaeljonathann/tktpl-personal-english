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
import com.dicoding.personalenglish.data.Word
import com.dicoding.personalenglish.databinding.FragmentHistoryItemBinding
import com.dicoding.personalenglish.ui.WordViewModel
import com.dicoding.personalenglish.util.InjectorUtils
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.sql.Date

class HistoryItemFragment: Fragment() {
    private lateinit var recyclerView: RecyclerView
    val ADD_WORD_REQUEST = 1
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

        val buttonAddWord: FloatingActionButton = mView.findViewById(R.id.button_add_note)
        buttonAddWord.setOnClickListener {
            val intent: Intent = Intent(mView.context, AddWordActivity::class.java)
            startActivityForResult(intent, ADD_WORD_REQUEST)
        }

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
            adapter.setWords(words)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_WORD_REQUEST && resultCode == RESULT_OK) {
            if (data != null) {
                val word: String = data.getStringExtra(AddWordActivity.EXTRA_WORD)?: ""

                val toInsertWord = Word( word, Date(10,10,10))
                wordViewModel.insert(toInsertWord)

                Toast.makeText(mView.context, "${wordViewModel.getAllWords()}", Toast.LENGTH_SHORT).show()

            }
        }
    }
}