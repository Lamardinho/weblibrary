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
    private val libraryPublicRepository: LibraryPublicRepository
) {

    @Transactional
    fun save(book: LibraryPublic): LibraryPublic {
        return libraryPublicRepository.save(book)
    }

    @Transactional
    fun saveAll(book: List<LibraryPublic>): MutableList<LibraryPublic> {
        return libraryPublicRepository.saveAll(book)
    }

    fun getAllBooks(): MutableList<LibraryPublic> {
        return libraryPublicRepository.findAll()
    }
}
