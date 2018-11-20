package com.dicoding.footballapps.api

import java.net.URL

class ApiRepository {
    fun doRequest(url: String): Deferred<String> = GlobalScope.async {
        URL(url).readText()
    }
}