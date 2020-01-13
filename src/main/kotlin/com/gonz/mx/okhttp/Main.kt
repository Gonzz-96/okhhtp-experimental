package com.gonz.mx.okhttp

import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

fun main() {

    printThreadName()

    val client = OkHttpClient()
    val request = Request.Builder()
        .url("https://pokeapi.co/api/v2/pokemon/ditto/")
        .get()
        .build()

    val response = client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            // no-op
        }

        override fun onResponse(call: Call, response: Response) {
            printThreadName()
            val myPokemon = Gson().fromJson(response.body?.string(), Pokemon::class.java)
            print("\n$myPokemon")
        }
    })
}

fun printThreadName() {
    println("***" + Thread.currentThread().name)
}

data class Pokemon(
    val name: String,
    val abilities: List<Ability>,
    val base_experience: Int,
    val forms: List<Form>,
    val id: Int
)

data class Ability(
    val ability: AbilityDetails,
    val is_hidden: Boolean,
    val slot: Int
)

data class AbilityDetails(
    val name: String,
    val url: String
)

data class Form(
    val name: String,
    val url: String
)