package id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.ui.WordDefinitionView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.databinding.FragmentWordDefinitionBinding
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.httpclient.WordData
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.R

class WordDefinitionFragment(
    val word: String,
    val definitions: List<WordData>
): Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var mView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentWordDefinitionBinding = FragmentWordDefinitionBinding.inflate(inflater, container, false)
        this.mView = binding.root
        this.recyclerView = mView.findViewById(R.id.rv_definition)
        binding.textViewWordToDef.setText(word)

        showRecyclerList()
        return mView
    }

    private fun showRecyclerList() {

        recyclerView.setLayoutManager(LinearLayoutManager(mView.context))
        recyclerView.setHasFixedSize(true)

        val adapter = WordDefinitionAdapter(definitions)
        recyclerView.setAdapter(adapter)
    }
}