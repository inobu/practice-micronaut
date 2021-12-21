package org.practice.micronaut.bookshelf.domain

import java.time.LocalDate

object TestDataFactory {
  private const val invalidLength = 101
  val invalidLengthName by lazy {
    (1..invalidLength).map { "a" }.reduce { first, second -> first + second }
  }
  const val validBookName: String = "Clean Architecture　達人に学ぶソフトウェアの構造と設計"
  const val validAuthorName: String = "ロバート・C・マーティン"
  val afterDate: LocalDate = LocalDate.of(2020, 11, 1)
  val beforeDate: LocalDate = LocalDate.of(2020, 10, 1)
  val currentDate: LocalDate = LocalDate.of(2020, 10, 31)
}
