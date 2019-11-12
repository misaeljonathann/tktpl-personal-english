package id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.httpclient

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ApiServices {

    @Headers(value = [
        "x-rapidapi-host: wordsapiv1.p.rapidapi.com",
        "x-rapidapi-key: eccaaf3181mshe1b4836a40f0225p1bb5dfjsn9bf4aedadd4f"
    ])
    @GET("words/{given_word}/definitions")
    fun getAllWords(
        @Path("given_word") givenWord: String
    ): Call<WordResponse>
}
