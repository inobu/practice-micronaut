package org.practice.micronaut.bookshelf.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory

inline fun <reified T : Any> anyLoggerFactory(): Logger {
  return LoggerFactory.getLogger(T::class.java)
}
