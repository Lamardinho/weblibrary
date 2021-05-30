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
    private val repository: LibraryPrivateRepository
) {
    @Transactional
    fun save(book: LibraryPrivate): LibraryPrivate {
        return repository.save(book)
    }

    @Transactional
    fun saveAll(book: List<LibraryPrivate>): MutableList<LibraryPrivate> {
        return repository.saveAll(book)
    }

    @Transactional
    fun deletePermanently(book: LibraryPrivate) {
        repository.delete(book)
    }

    fun getAllBooks(): MutableList<LibraryPrivate> {
        return repository.findAll()
    }
}
