package org.practice.micronaut.bookshelf.util

import arrow.core.Either

inline fun <K, T> Either<K, T>.tap(
    leftSideEffect: (K) -> Unit = {},
    rightSideEffect: (T) -> Unit = {}
): Either<K, T> {
  return when (this) {
    is Either.Left -> {
      this.also { leftSideEffect(this.a) }
    }
    is Either.Right -> {
      this.also { rightSideEffect(this.b) }
    }
  }
}
