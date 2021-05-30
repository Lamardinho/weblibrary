package com.example.weblibrary.module.libraries.repository

import com.example.weblibrary.model.libraries.LibraryPrivate
import com.example.weblibrary.model.libraries.LibraryPublic
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Репозиторий для работы с [LibraryPublic]
 */
@Repository
interface LibraryPublicRepository : JpaRepository<LibraryPublic, Long>


/**
 *  Репозиторий для работы с [LibraryPrivate]
 */
@Repository
interface LibraryPrivateRepository : JpaRepository<LibraryPrivate, Long>
