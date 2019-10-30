package com.dicoding.personalenglish.httpclient

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.newSingleThreadContext
import okhttp3.OkHttpClient
import okhttp3.Request


class HttpProvider {
    fun getDefinition(word: String): String {
        var client = OkHttpClient()
        var request = Request.Builder()
            .url("https://wordsapiv1.p.rapidapi.com/words/{$word}/definitions")
            .get()
            .addHeader("x-rapidapi-host", "wordsapiv1.p.rapidapi.com")
            .addHeader("x-rapidapi-key", "eccaaf3181mshe1b4836a40f0225p1bb5dfjsn9bf4aedadd4f")
            .build()

        var response = client.newCall(request).execute()
        return response.message()
    }
}