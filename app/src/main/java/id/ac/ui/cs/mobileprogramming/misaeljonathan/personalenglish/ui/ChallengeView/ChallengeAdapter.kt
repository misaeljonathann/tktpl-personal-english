package id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.ui.ChallengeView

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.R
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.data.Challenge

class ChallengeAdapter: RecyclerView.Adapter<ChallengeAdapter.ChallengeItemViewHolder>() {

    private var listChallenge: List<Challenge> = listOf()

    inner class ChallengeItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.text_view_challenge_title)
        var dueDate: TextView = itemView.findViewById(R.id.text_view_challenge_duedate)
        var target: TextView = itemView.findViewById(R.id.text_view_target)
        var targetBox: LinearLayout = itemView.findViewById(R.id.linear_layout_target)
    }

    override fun onCreateViewHolder(view: ViewGroup, viewType: Int): ChallengeItemViewHolder {
        val itemView: View = LayoutInflater.from(view.context).inflate(R.layout.challenge_item, view, false)
        return ChallengeItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ChallengeItemViewHolder, position: Int) {
        holder.title.text = listChallenge[position].title
        holder.dueDate.text = listChallenge[position].dueDate.toString()
        holder.target.text = "${listChallenge[position].currentCount}/${listChallenge[position].target}"

        if (listChallenge[position].currentCount >= listChallenge[position].target) {
            holder.targetBox.setBackgroundColor(Color.parseColor("#32CD32"))
        }
    }

    override fun getItemCount(): Int {
        return listChallenge.size
    }

    fun setListChallenges(givenListChallenge: List<Challenge>) {
        this.listChallenge = givenListChallenge
        notifyDataSetChanged()
    }
}