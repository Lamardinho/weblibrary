package com.example.weblibrary.rest.libraries.controller

import com.example.weblibrary.model.libraries.CommonBook
import com.example.weblibrary.model.libraries.LibraryPrivate
import com.example.weblibrary.model.libraries.LibraryPublic
import com.example.weblibrary.rest.libraries.dto.AddNewBookDTO
import com.example.weblibrary.rest.libraries.dto.CommonBooksDTO
import com.example.weblibrary.rest.libraries.service.LibraryPrivateService
import com.example.weblibrary.rest.libraries.service.LibraryPublicService
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

/**
 * Контроллер для работы с котологами библиотек
 */
@RestController
@RequestMapping("/library")
class LibraryController(
    private val libraryPublicService: LibraryPublicService,
    private val libraryPrivateService: LibraryPrivateService,
) {
    /**
     * Добавляет книгу в [LibraryPublic]
     */
    @PostMapping("add/public_book")
    fun addPublicBook(@RequestBody dto: AddNewBookDTO): CommonBooksDTO {
        val book = libraryPublicService.save(
            LibraryPublic(
                title = dto.title,
                author = dto.author,
                dateRelease = dto.dateRelease ?: LocalDate.MIN
            )
        )
        return CommonBooksDTO(
            id = book.id,
            title = book.title,
            author = book.author,
            dateRelease = book.dateRelease
        )
    }

    /**
     * Добавляет книгу в [LibraryPrivate]
     */
    @PostMapping("add/private_book")
    fun addPrivateBook(@RequestBody dto: AddNewBookDTO): CommonBooksDTO {
        val book = libraryPrivateService.save(
            LibraryPrivate(
                title = dto.title,
                author = dto.author,
                dateRelease = dto.dateRelease ?: LocalDate.MIN
            )
        )
        return CommonBooksDTO(
            id = book.id,
            title = book.title,
            author = book.author,
            dateRelease = book.dateRelease
        )
    }

    /**
     * Удаляет книгу из [LibraryPublic]
     */
    @DeleteMapping("/public_delete/{book_id}")
    fun deletePublicBook(@PathVariable("book_id") id: Long): String {
        val book = libraryPublicService.getById(id) ?: return "Не удалось найти книгу с id: $id"

        libraryPublicService.deletePermanently(book)
        return "Книга ${book.title} успешно удалена"
    }

    /**
     * Удаляет книгу из [LibraryPrivate]
     */
    @DeleteMapping("/private_delete/{book_id}")
    fun deletePrivateBook(@PathVariable("book_id") id: Long): String {
        val book = libraryPrivateService.getById(id) ?: return "Не удалось найти книгу с id: $id"

        libraryPrivateService.deletePermanently(book)
        return "Книга ${book.title} успешно удалена"
    }

    /**
     * Возвращает список публичных книг из всех библиотек
     */
    @GetMapping("/get_public_books")
    fun getPublicBooks(): ArrayList<CommonBooksDTO> {
        val publicBooks = libraryPublicService.getAllBooks()

        val publicBooksDTOs = ArrayList<CommonBooksDTO>()
        // проходимся по publicBooks, и наполняем publicBooksDTOs
        publicBooks.map {
            publicBooksDTOs.add(mapToDTO(it))
        }
        return publicBooksDTOs
    }

    /**
     * Возвращает список приватных книг из всех библиотек
     */
    @GetMapping("/get_private_books")
    fun getPrivateBooks(): ArrayList<CommonBooksDTO> {
        val privateBooks = libraryPrivateService.getAllBooks()

        val privateBooksDTOs = ArrayList<CommonBooksDTO>()
        // проходимся по privateBooks, и наполняем privateBooksDTOs
        privateBooks.map {
            privateBooksDTOs.add(mapToDTO(it))
        }
        return privateBooksDTOs
    }

    /**
     * Возвращает список всех книг из всех библиотек
     */
    @GetMapping("/get_all_books")
    fun getAllBooks(): ArrayList<CommonBooksDTO> {
        val allBooks = ArrayList<CommonBook>()
        // наполняем allBooks
        allBooks.addAll(libraryPrivateService.getAllBooks())
        allBooks.addAll(libraryPublicService.getAllBooks())

        val allBooksDTOs = ArrayList<CommonBooksDTO>()
        // проходимся по allBooks, и наполняем allBooksDTOs
        allBooks.map {
            allBooksDTOs.add(mapToDTO(it))
        }
        return allBooksDTOs
    }

    //todo: тут по идее я обычно использую mapstruct, но в целях упрощения и более быстрого выполнения задачи
    // mapstruct, validators и т.д. не использую
    private fun mapToDTO(book: CommonBook): CommonBooksDTO {
        return CommonBooksDTO(
            id = book.id,
            title = book.title,
            author = book.author,
            dateRelease = book.dateRelease,
        )
    }
}
