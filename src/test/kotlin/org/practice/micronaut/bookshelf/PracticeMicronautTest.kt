package org.practice.micronaut.bookshelf

import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import javax.inject.Inject
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

@MicronautTest
class PracticeMicronautTest {

  @Inject lateinit var application: EmbeddedApplication<*>

  @Test
  fun testItWorks() {
    Assertions.assertTrue(application.isRunning)
  }
}
