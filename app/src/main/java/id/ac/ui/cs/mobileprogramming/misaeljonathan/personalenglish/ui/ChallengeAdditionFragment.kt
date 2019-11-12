package id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.ui

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.databinding.FragmentAddChallengeBinding
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.data.Challenge
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.util.InjectorUtils
import java.sql.Date
import java.util.*

class ChallengeAdditionFragment: Fragment() {
    private lateinit var mView: View
    private lateinit var binding: FragmentAddChallengeBinding
    private lateinit var challengeViewModel: ChallengeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        this.binding = FragmentAddChallengeBinding.inflate(inflater, container, false)
        this.mView = binding.root

        val challengeFactory = InjectorUtils.provideChallengeViewModelFactory(mView.context)

        this.challengeViewModel = ViewModelProviders.of(this, challengeFactory)
            .get(ChallengeViewModel::class.java)

        saveChallenge()
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.buttonAddChallengeForm.setOnClickListener {
            saveChallenge()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    fun parseStringToChallengeDate(dateString: String): Date {
        var dateComponent: List<String> = dateString.split("/")
        return ChallengeDate(
            dateComponent[2].toInt(),
            dateComponent[1].toInt()-1,
            dateComponent[0].toInt()
        ).getDate()
    }

    fun saveChallenge() {
        val editTextTitle: EditText = binding.editTextChallengeTitle
        val editTextDuedate: EditText = binding.editTextChallengeDuedate
        val editTextTarget: EditText = binding.editTextChallengeTarget

        if (editTextTitle.text.trim().isEmpty() || editTextTarget.text.isEmpty() || editTextDuedate.text.isEmpty()) {
            Toast.makeText(mView.context, "Judul Jangan kosong Ya", Toast.LENGTH_SHORT).show()
            return
        }

        var targetDate = parseStringToChallengeDate(editTextDuedate.text.toString()) //recheck
        val toInsertChallenge = Challenge(
            title = editTextTitle.text.toString(),
            dueDate = targetDate,
            target = editTextTarget.text.toString().toInt(),
            currentCount = 0
        )
        challengeViewModel.insert(toInsertChallenge)
        fragmentManager?.popBackStack()
    }
}

data class ChallengeDate(
    var year: Int,
    var month: Int,
    var day: Int
) {
    fun getDate(): Date {
        var calendar: Calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        return Date(calendar.time.time)
    }
}