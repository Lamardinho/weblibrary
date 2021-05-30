package com.example.weblibrary.rest.libraries.dto

import java.time.LocalDate

/**
 *
 */
data class CommonBookDTO(
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


data class CommonEditBookDTO(
    var id: Long = 0L,
    var title: String? = null,
    var author: String? = null,
    var dateRelease: LocalDate? = null,
)
