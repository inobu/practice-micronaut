package org.practice.micronaut.bookshelf.domain.lib

sealed class DomainErr {
    object ValidationErr: DomainErr()
}