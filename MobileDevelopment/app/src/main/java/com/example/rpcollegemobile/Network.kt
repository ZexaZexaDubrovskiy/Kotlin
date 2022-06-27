package com.example.rpcollegemobile

import okhttp3.Call
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request

object Network {

    val client = OkHttpClient()

    fun getEventCall(): Call {
        val request = Request.Builder()
            .get()
            .url("http://188.68.31.11:8080/index_dubrovskiy.php")
            .build()
        return client.newCall(request)
    }


}