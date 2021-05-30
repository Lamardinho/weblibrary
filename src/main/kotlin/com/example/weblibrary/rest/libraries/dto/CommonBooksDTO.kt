package com.example.weblibrary.rest.libraries.dto

import java.time.LocalDate

/**
 *
 */
data class CommonBooksDTO(
    var id: Long = 0L,
    var title: String = "",
    var author: String = "",
    var dateRelease: LocalDate? = null,
)


/**
 * Для добавления новых книг
 */
data class AddNewBookDTO(
    var title: String = "",
    var author: String = "",
    var dateRelease: LocalDate? = null,
)
