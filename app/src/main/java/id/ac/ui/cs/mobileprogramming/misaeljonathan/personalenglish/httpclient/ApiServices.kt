package id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.httpclient

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path

interface ApiServices {

    @GET("words/{given_word}/definitions")
    fun getAllWords(
        @Path("given_word") givenWord: String,
        @Header("x-rapidapi-host") apiHost: String,
        @Header("x-rapidapi-key") apiKey: String
    ): Call<WordResponse>
}
