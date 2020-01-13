package com.gonz.mx.okhttp

import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

suspend fun main() {

    printThreadName()

    val client = OkHttpClient()
    val request = Request.Builder()
        .url("https://pokeapi.co/api/v2/pokemon/ditto/")
        .get()
        .build()

    val response = client.newCall(request).execute().body?.string()

    print(response)
}

fun printThreadName() {
    println("***" + Thread.currentThread().name)
}

data class Pokemon(
    val name: String
)
