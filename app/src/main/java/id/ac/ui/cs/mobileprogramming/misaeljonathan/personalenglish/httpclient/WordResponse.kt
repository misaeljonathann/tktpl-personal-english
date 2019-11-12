package id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.httpclient

import com.google.gson.annotations.SerializedName

data class WordResponse(
    @SerializedName("word")
    val word: String,

    @SerializedName("definitions")
    val definitions: List<WordData>
)
