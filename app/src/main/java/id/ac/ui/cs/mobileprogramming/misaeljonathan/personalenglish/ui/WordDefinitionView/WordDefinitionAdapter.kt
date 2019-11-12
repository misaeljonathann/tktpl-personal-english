package id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.ui.WordDefinitionView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.R
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.httpclient.WordData

class WordDefinitionAdapter(val listDefinition: List<WordData>): RecyclerView.Adapter<WordDefinitionAdapter.DefinitionViewHolder>() {

    inner class DefinitionViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var definition: TextView = itemView.findViewById(R.id.text_view_definition)
    }

    override fun onCreateViewHolder(view: ViewGroup, viewType: Int): DefinitionViewHolder {
        val itemView: View = LayoutInflater.from(view.context).inflate(R.layout.word_definition_item, view, false)
        return DefinitionViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DefinitionViewHolder, position: Int) {
        holder.definition.text = listDefinition[position].definition
    }

    override fun getItemCount(): Int {
        return listDefinition.size
    }
}