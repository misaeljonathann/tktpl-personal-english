package id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.databinding.FragmentHomepageBinding
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.httpclient.HttpProvider
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.httpclient.WordResponse
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.ui.WordViewModel
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.util.InjectorUtils
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.data.Word
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.httpclient.WordData
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.ui.ChallengeView.ChallengeAdapter
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.ui.ChallengeViewModel
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.ui.WordDefinitionView.WordDefinitionFragment
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.ui.ChallengeAdditionFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Date
import java.util.*

class HomepageFragment: Fragment() {
    private lateinit var mView: View
    private lateinit var wordViewModel: WordViewModel
    private lateinit var challengeViewModel: ChallengeViewModel
    private lateinit var challengeRecyclerView: RecyclerView
    private lateinit var httpClient: HttpProvider

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentHomepageBinding.inflate(inflater, container, false)
        this.mView = binding.root

        this.challengeRecyclerView = mView.findViewById(R.id.rv_challenge)
        this.httpClient = HttpProvider()

        setWordViewModel()
        showChallengeRecyclerList()
        return mView
    }

    fun setWordViewModel() {
        val wordFactory = InjectorUtils.provideWordViewModelFactory(mView.context)

        wordViewModel = ViewModelProviders.of(this, wordFactory)
            .get(WordViewModel::class.java)
        wordViewModel.getUnique().observe(this, Observer { listUnique ->
            showCurrentWordsNumber(listUnique.size)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val buttonAddWord: Button = mView.findViewById(R.id.add_button)
        buttonAddWord.setOnClickListener {
            saveWord()
        }

        val buttonAddChallenge: ImageView = mView.findViewById(R.id.image_view_add_challenge)
        buttonAddChallenge.setOnClickListener {
            navigateToOtherFragment(ChallengeAdditionFragment())
        }

        super.onViewCreated(view, savedInstanceState)
    }

    fun showCurrentWordsNumber(size: Int) {
        val dashboardTarget: TextView = mView.findViewById(R.id.text_view_dashboard_target)
        dashboardTarget.setText("$size unique words")
    }

    fun showChallengeRecyclerList() {
        val adapter = ChallengeAdapter()
        val challengeFactory = InjectorUtils.provideChallengeViewModelFactory(mView.context)

        challengeRecyclerView.layoutManager = LinearLayoutManager(mView.context)
        challengeRecyclerView.setHasFixedSize(true)
        challengeRecyclerView.adapter = adapter

        challengeViewModel = ViewModelProviders.of(this, challengeFactory)
            .get(ChallengeViewModel::class.java)
        challengeViewModel.getAllChallenge().observe(this, Observer { listChallenge ->
            adapter.setListChallenges(listChallenge)
        })
    }

    fun httpGetDefinition(givenWord: Word) {
        HttpProvider().services.getAllWords(givenWord.word).enqueue(object: Callback<WordResponse> {
            override fun onResponse(call: Call<WordResponse>, response: Response<WordResponse>) {
                if (response.code() == 200) {
                    response.body()?.let { wordResponse ->
                        goToWordDefinitionPage(wordResponse.word, wordResponse.definitions)
                        wordViewModel.insert(givenWord)
                    }
                } else {
                    Toast.makeText(mView.context, "Response Error, English word not found/not valid", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<WordResponse>, t: Throwable) {
                Toast.makeText(mView.context, "$t", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun goToWordDefinitionPage(word: String, definitions: List<WordData>?) {
        if (definitions != null) {
            navigateToOtherFragment(WordDefinitionFragment(word, definitions))
        } else {
            Toast.makeText(mView.context, "Definition Null", Toast.LENGTH_SHORT).show()
        }
    }

    fun navigateToOtherFragment(fragment: Fragment) {
        val fragmentTransaction: FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.fragment_home, fragment, fragment.toString())
        fragmentTransaction?.addToBackStack(fragment.toString())
        fragmentTransaction?.commit()
    }

    fun saveWord() {
        val editText: EditText = mView.findViewById(R.id.edit_text_word)
        var editTextWord: String = editText.text.toString()

        if (editTextWord.trim().isEmpty()) {
            Toast.makeText(mView.context, "Jangan Kosong Ya", Toast.LENGTH_SHORT).show()
            return
        }

        challengeViewModel.addWordCount()

        val data = Intent()
        data.putExtra(EXTRA_WORD, editTextWord)

        val word: String = data.getStringExtra(EXTRA_WORD) ?: ""
        val currentTime = Date(Calendar.getInstance().time.time)
        val toInsertWord = Word(
            word,
            currentTime
        )

        httpGetDefinition(toInsertWord)
    }

    companion object {
        val EXTRA_WORD = "com.misael.personalenglish.EXTRA_WORD"
    }
}