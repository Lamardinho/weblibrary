package com.example.weblibrary.rest.libraries.controller

import com.example.weblibrary.model.libraries.CommonBook
import com.example.weblibrary.model.libraries.LibraryPrivate
import com.example.weblibrary.model.libraries.LibraryPublic
import com.example.weblibrary.rest.libraries.dto.AddNewBookDTO
import com.example.weblibrary.rest.libraries.dto.CommonBookDTO
import com.example.weblibrary.rest.libraries.dto.CommonEditBookDTO
import com.example.weblibrary.rest.libraries.service.LibraryPrivateService
import com.example.weblibrary.rest.libraries.service.LibraryPublicService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.http.ResponseEntity.status
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
    @PostMapping("/add/public_book")
    fun addPublicBook(@RequestBody dto: AddNewBookDTO): CommonBookDTO {
        val book = libraryPublicService.save(
            LibraryPublic(
                title = dto.title,
                author = dto.author,
                dateRelease = dto.dateRelease ?: LocalDate.MIN
            )
        )
        return mapperBookToCommonBookDTO(book)
    }

    /**
     * Добавляет книгу в [LibraryPrivate]
     */
    @PostMapping("/add/private_book")
    fun addPrivateBook(@RequestBody dto: AddNewBookDTO): CommonBookDTO {
        val book = libraryPrivateService.save(
            LibraryPrivate(
                title = dto.title,
                author = dto.author,
                dateRelease = dto.dateRelease ?: LocalDate.MIN
            )
        )
        return mapperBookToCommonBookDTO(book)
    }

    /**
     * Редактирует [LibraryPublic]
     */
    @PatchMapping("/edit/public_book")
    fun editPublicBook(@RequestBody dto: CommonEditBookDTO): ResponseEntity<ResponseBook> {
        val book = libraryPublicService.getById(dto.id)
            ?: return status(HttpStatus.NOT_ACCEPTABLE).body(
                ResponseBook(null, NOT_FIND_BOOK_ID + "${dto.id}")
            )

        val edited = mapperCommonEditBookDtoToBook(book, dto)

        val saved = libraryPublicService.save(edited as LibraryPublic)
        if (saved.id <= 0) return status(HttpStatus.NOT_ACCEPTABLE).body(
            ResponseBook(null, FAILED_EDIT_BOOK)
        )

        return ok().body(ResponseBook(mapperBookToCommonBookDTO(saved), BOOK_SUCCESSFULLY_EDITED))
    }

    /**
     * Редактирует [LibraryPrivate]
     */
    @PatchMapping("/edit/private_book")
    fun editPrivateBook(@RequestBody dto: CommonEditBookDTO): ResponseEntity<ResponseBook> {
        val book = libraryPrivateService.getById(dto.id)
            ?: return status(HttpStatus.NOT_ACCEPTABLE).body(
                ResponseBook(null, NOT_FIND_BOOK_ID + "${dto.id}")
            )

        val edited = mapperCommonEditBookDtoToBook(book, dto)

        val saved = libraryPrivateService.save(edited as LibraryPrivate)
        if (saved.id <= 0) return status(HttpStatus.NOT_ACCEPTABLE).body(
            ResponseBook(null, FAILED_EDIT_BOOK)
        )

        return ok().body(ResponseBook(mapperBookToCommonBookDTO(saved), BOOK_SUCCESSFULLY_EDITED))
    }

    /**
     * Удаляет книгу из [LibraryPublic]
     */
    @DeleteMapping("/public_delete/{book_id}")
    fun deletePublicBook(@PathVariable("book_id") id: Long): String {
        val book = libraryPublicService.getById(id) ?: return NOT_FIND_BOOK_ID + "$id"

        libraryPublicService.deletePermanently(book)
        return "Книга ${book.title} успешно удалена"
    }

    /**
     * Удаляет книгу из [LibraryPrivate]
     */
    @DeleteMapping("/private_delete/{book_id}")
    fun deletePrivateBook(@PathVariable("book_id") id: Long): String {
        val book = libraryPrivateService.getById(id) ?: return NOT_FIND_BOOK_ID + "$id"

        libraryPrivateService.deletePermanently(book)
        return "Книга ${book.title} успешно удалена"
    }

    /**
     * Возвращает список публичных книг из всех библиотек
     */
    @GetMapping("/get_public_books")
    fun getPublicBooks(): ArrayList<CommonBookDTO> {
        val publicBooks = libraryPublicService.getAllBooks()

        val publicBooksDTOs = ArrayList<CommonBookDTO>()
        // проходимся по publicBooks, и наполняем publicBooksDTOs
        publicBooks.map {
            publicBooksDTOs.add(mapperBookToCommonBookDTO(it))
        }
        return publicBooksDTOs
    }

    /**
     * Возвращает список приватных книг из всех библиотек
     */
    @GetMapping("/get_private_books")
    fun getPrivateBooks(): ArrayList<CommonBookDTO> {
        val privateBooks = libraryPrivateService.getAllBooks()

        val privateBooksDTOs = ArrayList<CommonBookDTO>()
        // проходимся по privateBooks, и наполняем privateBooksDTOs
        privateBooks.map {
            privateBooksDTOs.add(mapperBookToCommonBookDTO(it))
        }
        return privateBooksDTOs
    }

    /**
     * Возвращает список всех книг из всех библиотек
     */
    @GetMapping("/get_all_books")
    fun getAllBooks(): ArrayList<CommonBookDTO> {
        val allBooks = ArrayList<CommonBook>()
        // наполняем allBooks
        allBooks.addAll(libraryPrivateService.getAllBooks())
        allBooks.addAll(libraryPublicService.getAllBooks())

        val allBooksDTOs = ArrayList<CommonBookDTO>()
        // проходимся по allBooks, и наполняем allBooksDTOs
        allBooks.map {
            allBooksDTOs.add(mapperBookToCommonBookDTO(it))
        }
        return allBooksDTOs
    }

    /**
     * Перемещает из [LibraryPublic] в [LibraryPrivate]
     */
    @GetMapping("/move/public_to_private/{book_id}")
    fun movePublicToPrivate(@PathVariable("book_id") id: Long): ResponseEntity<ResponseBook> {
        val book = libraryPublicService.getById(id)
            ?: return status(HttpStatus.NOT_ACCEPTABLE).body(
                ResponseBook(null, NOT_FIND_BOOK_ID + "$id")
            )

        val moved = libraryPublicService.moveToPrivate(book)
        return if (moved.id > 0) {
            ok().body(ResponseBook(mapperBookToCommonBookDTO(moved), MOVED))
        } else {
            status(HttpStatus.NOT_ACCEPTABLE).body(ResponseBook(null, NOT_MOVED))
        }
    }

    /**
     * Перемещает из [LibraryPrivate] в [LibraryPublic]
     */
    @GetMapping("/move/private_to_public/{book_id}")
    fun movePrivateToPublic(@PathVariable("book_id") id: Long): ResponseEntity<ResponseBook> {
        val book = libraryPrivateService.getById(id)
            ?: return status(HttpStatus.NOT_ACCEPTABLE).body(
                ResponseBook(null, NOT_FIND_BOOK_ID + "$id")
            )

        val moved = libraryPrivateService.moveToPublic(book)
        return if (moved.id > 0) {
            ok().body(ResponseBook(mapperBookToCommonBookDTO(moved), MOVED))
        } else {
            status(HttpStatus.NOT_ACCEPTABLE).body(ResponseBook(null, NOT_MOVED))
        }
    }

    //todo: тут по идее я обычно использую mapstruct, но в целях упрощения и более быстрого выполнения задачи
    // mapstruct, validators и т.д. не использую
    private fun mapperCommonEditBookDtoToBook(book: CommonBook, dto: CommonEditBookDTO): CommonBook {
        if (dto.author != null) book.author = dto.author!!
        if (dto.title != null) book.title = dto.title!!
        if (dto.dateRelease != null) book.dateRelease = dto.dateRelease!!

        return book
    }

    private fun mapperBookToCommonBookDTO(book: CommonBook): CommonBookDTO {
        return CommonBookDTO(
            id = book.id,
            author = book.author,
            title = book.title,
            dateRelease = book.dateRelease
        )
    }

    private companion object {
        private const val BOOK_SUCCESSFULLY_EDITED = "Книга успешно отредактирована"
        private const val FAILED_EDIT_BOOK = "Не удалось отредактировать книгу"
        private const val NOT_FIND_BOOK_ID = "Не удалось найти книгу с id: "
        private const val MOVED = "Книга успешно перемещена"
        private const val NOT_MOVED = "Не удалось перенести книгу"
    }
}

data class ResponseBook(
    val commonBookDTO: CommonBookDTO?,
    val message: String
)
