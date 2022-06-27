package com.example.proj3

import android.util.Log
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException

class NewsRepository {
    fun loadNewsSync(callback: (List<Article>) -> Unit) {
        Thread {
            try {
                val call = Network.getNewsCall()
                val response = call.execute()
                if (response.isSuccessful) {
                    val responseBodyString = response.body?.string() as String
                    val news: List<Article> = parseResponse(responseBodyString)
                    callback(news)
                } else {
                    Log.e("News Server", "Server error responses")
                    callback(listOf())
                }
            } catch (e: IOException) {
                Log.e("News Server", "${e.message}", e)
                callback(listOf())
            }
        }.start()
    }

    private lateinit var successfulCallback: (List<Article>) -> Unit
    private lateinit var failureCallback: (e: Throwable) -> Unit
    fun loadNewsAsync(
        successfulCallback: (List<Article>) -> Unit,
        failureCallback: (e: Throwable) -> Unit
    ):Call {
        this.successfulCallback = successfulCallback
        this.failureCallback = failureCallback

        val call = Network.getNewsCall()
        call.enqueue(asyncCallback)
        return call
    }

    private val asyncCallback = object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            Log.e("News Server", "${e.message}", e)
            failureCallback(e)
        }

        override fun onResponse(call: Call, response: Response) {
            if (response.isSuccessful) {
                val responseBodyString = response.body?.string() as String
                val news: List<Article> = parseResponse(responseBodyString)
                successfulCallback(news)
            } else {
                Log.e("News Server", "Server error responses")
                failureCallback(Throwable("Server error responses"))
            }
        }
    }

    private fun parseResponse(response: String): List<Article> {
        try {
            val newsArray = JSONArray(response)

            return (0 until newsArray.length()).map { index ->
                newsArray.getJSONObject(index)
            }.map { articleObject ->
                val id = articleObject.getInt("eventId")
                val name = articleObject.getString("eventName")
                val announce = articleObject.getString("eventAnnounce")
                val text = articleObject.getString("eventText")
                val image = articleObject.getString("eventImagePath")
                Article(id, name, image, announce, text, "пык-мык")
            }
        } catch (e: JSONException) {
            return emptyList()
        }
    }
}