package org.practice.micronaut.bookshelf.domain.lib

import java.nio.ByteBuffer
import java.util.*

fun UUID.toBytes(): ByteArray {
    val buffer = ByteBuffer.allocate(16)
    buffer.putLong(mostSignificantBits)
    buffer.putLong(leastSignificantBits)
    return buffer.array()
}

fun ByteArray.toUUID(): UUID {
    val buffer = ByteBuffer.wrap(this)
    return UUID(buffer.long, buffer.long)
}