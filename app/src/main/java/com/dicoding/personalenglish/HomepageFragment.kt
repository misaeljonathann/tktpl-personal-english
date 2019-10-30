package com.dicoding.personalenglish

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.dicoding.personalenglish.data.Word
import com.dicoding.personalenglish.databinding.FragmentHomepageBinding
import com.dicoding.personalenglish.httpclient.HttpProvider
import com.dicoding.personalenglish.ui.WordViewModel
import com.dicoding.personalenglish.util.InjectorUtils
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import java.sql.Date
import java.util.*

class HomepageFragment: Fragment() {
    private lateinit var mView: View
    val ADD_WORD_REQUEST = 1
    private lateinit var wordViewModel: WordViewModel
    private lateinit var httpClient: HttpProvider

    var thread = newSingleThreadContext("httpRequest") as CoroutineDispatcher

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentHomepageBinding = FragmentHomepageBinding.inflate(inflater, container, false)
        this.mView = binding.root
        this.httpClient = HttpProvider()

        val buttonAddWord: Button = mView.findViewById(R.id.add_button)

        buttonAddWord.setOnClickListener {
            saveWord()
        }

        val factory = InjectorUtils.provideQuotesViewModelFactory(mView.context)
        wordViewModel = ViewModelProviders.of(this, factory)
            .get(WordViewModel::class.java)

        return mView
    }

    fun saveWord() {
        val editText: EditText = mView.findViewById(R.id.edit_text_word)
        var editTextWord: String = editText.text.toString()

        if (editTextWord.trim().isEmpty()) {
            Toast.makeText(mView.context, "Jangan kosong tong", Toast.LENGTH_SHORT).show()
            return
        }

        val data: Intent = Intent()
        data.putExtra(AddWordActivity.EXTRA_WORD, editTextWord)

        val word: String = data.getStringExtra(AddWordActivity.EXTRA_WORD)?: ""
        val currentTime = Date(Calendar.getInstance().time.time)
        val toInsertWord = Word( word, currentTime)
        wordViewModel.insert(toInsertWord)

        Toast.makeText(mView.context, "${wordViewModel.getAllWords()}", Toast.LENGTH_SHORT).show()
    }
}