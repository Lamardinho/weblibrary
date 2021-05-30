package com.example.weblibrary.rest.libraries.service

import com.example.weblibrary.model.libraries.LibraryPrivate
import com.example.weblibrary.model.libraries.LibraryPublic
import com.example.weblibrary.module.libraries.repository.LibraryPrivateRepository
import com.example.weblibrary.module.libraries.repository.LibraryPublicRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

/**
 * Сервис для работы с [LibraryPrivate]
 */
@Service
class LibraryPrivateService(
    private val libraryPrivateRepository: LibraryPrivateRepository,
    private val libraryPublicRepository: LibraryPublicRepository
) {
    @Transactional
    fun save(book: LibraryPrivate): LibraryPrivate {
        return libraryPrivateRepository.save(book)
    }

    fun getAllBooks(): MutableList<LibraryPrivate> {
        return libraryPrivateRepository.findAll()
    }

    fun getById(id: Long): LibraryPrivate? {
        return libraryPrivateRepository.findById(id).orElse(null)
    }

    @Transactional
    fun deletePermanently(book: LibraryPrivate) {
        libraryPrivateRepository.delete(book)
    }

    @Transactional
    fun moveToPublic(book: LibraryPrivate): LibraryPublic {
        // сохраняем в public
        val moved = libraryPublicRepository.save(
            LibraryPublic(
                author = book.author,
                title = book.title,
                dateRelease = book.dateRelease
            )
        )

        // удаляем из private
        libraryPrivateRepository.delete(book)

        return moved
    }
}
