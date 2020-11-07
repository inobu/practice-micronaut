package org.practice.micronaut.bookshelf.domain.lib

import java.nio.ByteBuffer
import java.util.*

fun UUID.uuidToBytes(): ByteArray {
    val buffer = ByteBuffer.allocate(16)
    buffer.putLong(mostSignificantBits)
    buffer.putLong(leastSignificantBits)
    return buffer.array()
}

fun bytesToUuid(bytes: ByteArray?): UUID? {
    if (bytes == null) {
        return null
    }
    val buffer = ByteBuffer.wrap(bytes)
    return UUID(buffer.long, buffer.long)
}