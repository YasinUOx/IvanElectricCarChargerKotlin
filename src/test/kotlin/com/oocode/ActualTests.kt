package com.oocode

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.math.exp

class InterceptingStdOutTest {
    @Test
    fun canInterceptStdOut() {
        val expectedOutput = """Best times to plug in:
Mon, 14 Apr 2025 12:00:00 GMT
Mon, 14 Apr 2025 12:30:00 GMT
Mon, 14 Apr 2025 13:00:00 GMT"""
        assertThat(calculateBestTimes().trim(), equalTo(expectedOutput));
    }

    private var oldOut: PrintStream? = null

    @BeforeEach
    fun rememberRealSystemOut() {
        oldOut = System.out
    }

    @AfterEach
    fun restoreSystemOut() {
        System.setOut(oldOut)
    }
}