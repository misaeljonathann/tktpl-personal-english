package com.dicoding.personalenglish

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.personalenglish.Common.Common
import com.dicoding.personalenglish.data.HistoryDate
import com.dicoding.personalenglish.data.HistoryItem
import com.dicoding.personalenglish.data.HistoryWord
import com.dicoding.personalenglish.data.Word
import java.lang.IllegalArgumentException

class HistoryAdapter: RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    private var historyItems: List<HistoryItem> = listOf()

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
            HistoryItem.VIEWTYPE_DATE -> {
                val itemView: View = LayoutInflater.from(view.context).inflate(R.layout.history_group_header, view, false)
                return HeaderViewHolder(itemView)
            }
            HistoryItem.VIEWTYPE_WORD -> {
                val itemView: View = LayoutInflater.from(view.context).inflate(R.layout.history_item, view, false)
                return ListViewHolder(itemView)
            }
            else -> throw IllegalArgumentException("Invalid View Type")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder.getItemViewType()) {
            HistoryItem.VIEWTYPE_WORD -> {
                var currentItem = historyItems.get(position) as HistoryWord
                var itemHolder: ListViewHolder = holder as ListViewHolder
                itemHolder.word.setText(currentItem.word)
            }

            HistoryItem.VIEWTYPE_DATE -> {
                var currentItem = historyItems.get(position) as HistoryDate
                var itemHolder: HeaderViewHolder = holder as HeaderViewHolder
                itemHolder.time.setText(currentItem.date)
            }
        }
    }

    fun setWords(items: List<HistoryItem>) {
        historyItems = items
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return historyItems.get(position).getType()
    }

    override fun getItemCount(): Int {
        return historyItems.size
    }
}