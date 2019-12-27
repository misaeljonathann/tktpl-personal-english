package id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.opengl.GLES10
import android.opengl.GLSurfaceView
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
import javax.microedition.khronos.opengles.GL10

class HomepageFragment: Fragment() {
    private lateinit var mView: View
    private lateinit var wordViewModel: WordViewModel
    private lateinit var challengeViewModel: ChallengeViewModel
    private lateinit var challengeRecyclerView: RecyclerView
    private lateinit var httpClient: HttpProvider
    private lateinit var connManager: ConnectivityManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentHomepageBinding.inflate(inflater, container, false)
        this.mView = binding.root

        this.challengeRecyclerView = mView.findViewById(R.id.rv_challenge)
        this.httpClient = HttpProvider()
        this.connManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        binding.glSurface.setRenderer(GLRenderer())

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

        val API_HOST = getApiHostFromJNI()
        val API_KEY = String(android.util.Base64.decode(getApiKeyFromJNI(), android.util.Base64.DEFAULT), Charsets.UTF_8)

        HttpProvider().services.getAllWords(
            givenWord.word, API_HOST, API_KEY).enqueue(object: Callback<WordResponse> {
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
                Toast.makeText(mView.context, "Error : ${getApiKeyFromJNI()}", Toast.LENGTH_SHORT).show()
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

        val mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        if (!mWifi.isConnected) {
            Toast.makeText(context, "WiFi tidak terhubung, tolong dinyalakan dulu", Toast.LENGTH_LONG).show()
        }

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

    class GLRenderer : GLSurfaceView.Renderer {
        var color = 0f
        var colorVelocity = 1f/60f

        override fun onDrawFrame(gl: GL10){
            if (color > 1 || color < 0){
                colorVelocity = -colorVelocity
            }
            color += colorVelocity

            gl.glClearColor(color * 0.5f, color, color, 1f)
            gl.glClear(GLES10.GL_COLOR_BUFFER_BIT)
        }

        override fun onSurfaceCreated(p0: GL10?, p1: javax.microedition.khronos.egl.EGLConfig?) {}
        override fun onSurfaceChanged(gl: GL10, width: Int, height: Int){}
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun getApiKeyFromJNI(): String
    external fun getApiHostFromJNI(): String

    companion object {
        val EXTRA_WORD = "com.misael.personalenglish.EXTRA_WORD"

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}