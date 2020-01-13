package com.gonz.mx.okhttp

import com.google.gson.Gson
import okhttp3.*
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException
import org.hamcrest.CoreMatchers.`is` as iss

@RunWith(JUnit4::class)
class MockWebServerExperimental {

    private lateinit var mockServer: MockWebServer
    private lateinit var client: OkHttpClient

    @Before
    fun setUp() {
        mockServer = MockWebServer()
        mockServer.start()

        val myResponse = MockResponse()
        myResponse.setResponseCode(200)
        myResponse.setBody("""
            {
                name: "ditto"
            }
        """.trimIndent())

        mockServer.enqueue(myResponse)

        client = OkHttpClient()
    }

    @After
    fun tearDown() {
        mockServer.shutdown()
    }

    @Test
    fun testing_MockWebServer() {

        val myRequest = Request.Builder()
            .url(mockServer.url("/ditto"))
            .get()
            .build()

        client.newCall(myRequest).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call, response: Response) {
                val responsePokemon = Gson().fromJson(response.body?.string(), Pokemon::class.java)

                assertThat(responsePokemon, iss( Pokemon("ditto") ))
            }
        })
    }

}