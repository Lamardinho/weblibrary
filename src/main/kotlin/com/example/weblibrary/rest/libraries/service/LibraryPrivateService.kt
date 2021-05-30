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

    fun getAllBooks(): MutableList<LibraryPrivate> {
        return repository.findAll()
    }

    fun getById(id: Long): LibraryPrivate? {
        return repository.findById(id).orElse(null)
    }

    @Transactional
    fun deletePermanently(book: LibraryPrivate) {
        repository.delete(book)
    }
}
