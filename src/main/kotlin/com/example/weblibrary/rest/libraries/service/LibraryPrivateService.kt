package com.example.weblibrary.rest.libraries.service

import com.example.weblibrary.model.libraries.LibraryPrivate
import com.example.weblibrary.module.libraries.repository.LibraryPrivateRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

/**
 * Сервис для работы с [LibraryPrivate]
 */
@Service
class LibraryPrivateService(
    private val libraryPrivateRepository: LibraryPrivateRepository
) {
    @Transactional
    fun save(book: LibraryPrivate): LibraryPrivate {
        return libraryPrivateRepository.save(book)
    }

    @Transactional
    fun saveAll(book: List<LibraryPrivate>): MutableList<LibraryPrivate> {
        return libraryPrivateRepository.saveAll(book)
    }

    @Transactional
    fun getAllBooks(): MutableList<LibraryPrivate> {
        return libraryPrivateRepository.findAll()
    }
}
