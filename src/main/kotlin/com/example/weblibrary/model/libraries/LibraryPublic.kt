package com.example.weblibrary.model.libraries

import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "t_public_books")
data class LibraryPublic(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    override var id: Long = 0L,

    /** Наименование книги */
    @Column(name = "title", length = 100)
    override var title: String = "",

    /** Автор книги */
    @Column(name = "author", length = 100)
    override var author: String = "",

    /** Дата выхода */
    @Column(name = "date_release")
    override var dateRelease: LocalDate = LocalDate.MIN,
) : CommonBook
