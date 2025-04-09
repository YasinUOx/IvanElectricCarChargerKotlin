package com.oocode

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.contains
import com.natpryce.hamkrest.containsSubstring
import com.natpryce.hamkrest.equalTo
import com.opencsv.CSVReader
import okhttp3.OkHttpClient
import org.http4k.client.JavaHttpClient
import org.http4k.client.OkHttp
import org.http4k.core.*
import org.http4k.server.Http4kServer
import org.http4k.server.SunHttp
import org.http4k.server.asServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.StringReader
import java.time.LocalDate

class ExampleUsingHttpServerToFakeExternalServiceTest {
    private var server: Http4kServer? = null
    val nationalGridEsoDataUrl =
        "https://api.nationalgrideso.com/dataset/91c0c70e-0ef5-4116-b6fa-7ad084b5e0e8/resource/db6c038f-98af-4570-ab60-24d71ebd0ae5/download/embedded-forecast.csv"

    @Test
    fun canReadUrl() {


        val httpClient = OkHttp(OkHttpClient.Builder().followRedirects(true).build())
        val contents = httpClient(Request(Method.GET, nationalGridEsoDataUrl)).bodyString()

        assertThat(contents, containsSubstring(LocalDate.now().toString()))
    }

    @BeforeEach
    fun startLocalServerPretendingToBeNationalGridEso() {
        val app: HttpHandler = { Response(Status.OK).body("some test data") }
        // creates a server on port 8123
        server = app.asServer(SunHttp(8123))
        server?.start()
    }

    @AfterEach
    fun stopLocalServerPretendingToBeNationalGridEso() {
        server?.close()
    }
}
