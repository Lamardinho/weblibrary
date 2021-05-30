package com.example.weblibrary.model.libraries

import java.time.LocalDate

interface CommonBook {
    var id: Long
    var title: String
    var author: String
    var dateRelease: LocalDate
}
