package com.example.weblibrary.rest.libraries.service

import com.example.weblibrary.model.libraries.LibraryPublic
import com.example.weblibrary.module.libraries.repository.LibraryPublicRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

/**
 * Сервис для работы с [LibraryPublic]
 */
@Service
class LibraryPublicService(
    private val repository: LibraryPublicRepository
) {
    @Transactional
    fun save(book: LibraryPublic): LibraryPublic {
        return repository.save(book)
    }

    @Transactional
    fun saveAll(book: List<LibraryPublic>): MutableList<LibraryPublic> {
        return repository.saveAll(book)
    }

    @Transactional
    fun deletePermanently(book: LibraryPublic) {
        repository.delete(book)
    }

    fun getAllBooks(): MutableList<LibraryPublic> {
        return repository.findAll()
    }
}
