package com.dicoding.personalenglish

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.personalenglish.Common.Common
import com.dicoding.personalenglish.data.Word
import java.lang.IllegalArgumentException

class HistoryAdapter: RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    private var listWords: List<Word> = listOf()

    open inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    inner class ListViewHolder(itemView: View) : ViewHolder(itemView) {
        var word: TextView = itemView.findViewById(R.id.text_view_word)
//        var time: TextView = itemView.findViewById(R.id.text_view_time)
    }

    inner class HeaderViewHolder(itemView: View) : ViewHolder(itemView) {
        var time: TextView = itemView.findViewById(R.id.text_view_head)
    }

    override fun onCreateViewHolder(view: ViewGroup, viewType: Int): ViewHolder {
        when (viewType) {
            Common.VIEWTYPE_GROUP.code -> {
                val itemView: View = LayoutInflater.from(view.context).inflate(R.layout.history_group_header, view, false)
                return HeaderViewHolder(itemView)
            }
            Common.VIEWTYPE_WORD.code -> {
                val itemView: View = LayoutInflater.from(view.context).inflate(R.layout.history_item, view, false)
                return ListViewHolder(itemView)
            }
            else -> throw IllegalArgumentException("Invalid View Type")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var currentWords = listWords.get(position)
        var itemHolder: ListViewHolder = holder as ListViewHolder

        itemHolder.word.setText(currentWords.word)
//        holder.time.setText(currentWords.time.toString())

//        holder.itemView.setOnClickListener {
//            onItemClickCallback.onItemClicked(listWords[holder.adapterPosition], position)
//        }
    }

    fun setWords(reminders: List<Word>) {
        listWords = reminders
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return listWords.get(position).getType()
    }

    override fun getItemCount(): Int {
        return listWords.size
    }
}