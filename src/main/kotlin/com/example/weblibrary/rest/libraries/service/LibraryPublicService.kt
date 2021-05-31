package com.example.weblibrary.rest.libraries.service

import com.example.weblibrary.model.libraries.LibraryPrivate
import com.example.weblibrary.model.libraries.LibraryPublic
import com.example.weblibrary.module.libraries.repository.LibraryPrivateRepository
import com.example.weblibrary.module.libraries.repository.LibraryPublicRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

/**
 * Сервис для работы с [LibraryPublic]
 */
@Service
class LibraryPublicService(
    private val libraryPublicRepository: LibraryPublicRepository,
    private val libraryPrivateRepository: LibraryPrivateRepository
) {

    fun save(book: LibraryPublic): LibraryPublic {
        return libraryPublicRepository.save(book)
    }

    fun getAllBooks(): MutableList<LibraryPublic> {
        return libraryPublicRepository.findAll()
    }

    fun getById(id: Long): LibraryPublic? {
        return libraryPublicRepository.findById(id).orElse(null)
    }

    fun deletePermanently(book: LibraryPublic) {
        libraryPublicRepository.delete(book)
    }

    @Transactional
    fun moveToPrivate(book: LibraryPublic): LibraryPrivate {
        // сохраняем в private
        val moved = libraryPrivateRepository.save(
            LibraryPrivate(
                author = book.author,
                title = book.title,
                dateRelease = book.dateRelease
            )
        )

        // удаляем из public
        libraryPublicRepository.delete(book)

        return moved
    }
}
