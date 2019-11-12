package id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.ui.HistoryView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.R
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.data.HistoryDate
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.data.HistoryItem
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.data.HistoryWord
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.databinding.HistoryGroupHeaderBinding
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.databinding.HistoryItemBinding
import java.lang.IllegalArgumentException

class HistoryAdapter: RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    private var historyItems: List<HistoryItem> = listOf()

    open inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    inner class ListViewHolder(val binding: HistoryItemBinding) : ViewHolder(binding.root) {

        fun bind(history: HistoryWord) {
            binding.historyWord = HistoryWord(history.word)
            binding.executePendingBindings()
        }
    }

    inner class HeaderViewHolder(val binding: HistoryGroupHeaderBinding) : ViewHolder(binding.root) {

        fun bind(history: HistoryDate) {
            binding.historyDate = HistoryDate(history.date)
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(view: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(view.context)

        when (viewType) {
            HistoryItem.VIEWTYPE_DATE -> {
                val binding: HistoryGroupHeaderBinding = DataBindingUtil.inflate(inflater, R.layout.history_group_header, view, false)
                return HeaderViewHolder(binding)
            }
            HistoryItem.VIEWTYPE_WORD -> {
                val binding: HistoryItemBinding = DataBindingUtil.inflate(inflater, R.layout.history_item, view, false)
                return ListViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid View Type")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder.getItemViewType()) {
            HistoryItem.VIEWTYPE_WORD -> {
                var currentItem = historyItems.get(position) as HistoryWord
                var itemHolder: ListViewHolder = holder as ListViewHolder
                itemHolder.bind(currentItem)
            }

            HistoryItem.VIEWTYPE_DATE -> {
                var currentItem = historyItems.get(position) as HistoryDate
                var itemHolder: HeaderViewHolder = holder as HeaderViewHolder
                itemHolder.bind(currentItem)
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