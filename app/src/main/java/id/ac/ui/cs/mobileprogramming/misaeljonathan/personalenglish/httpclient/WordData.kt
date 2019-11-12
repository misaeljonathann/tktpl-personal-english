package id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.httpclient

import com.google.gson.annotations.SerializedName

data class WordData (
    @SerializedName("definition")
    var definition: String?,

    @SerializedName("partOfSpeech")
    var partOfSpeech: String?
)
