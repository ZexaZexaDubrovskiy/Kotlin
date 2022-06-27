package com.example.proj3

import okhttp3.Call
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request

object Network {
    //http://rpcollege.ru/event/list/json?limit=3&offset=0
    val client = OkHttpClient()

    fun getNewsCall() : Call {
        val url = HttpUrl.Builder()
            .scheme("http")
            .host("rpcollege.ru")
            .addEncodedPathSegments("event/list/json")
            .addQueryParameter("limit", "15")
            .addQueryParameter("offset", "0")
            .build()
        val request = Request.Builder()
            .get()
            .url(url)
            .build()
        //client
       return client.newCall(request)
    }
}