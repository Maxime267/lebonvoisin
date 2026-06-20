package com.example.lebonvoisin.dataclass.review

data class Review(
    var ownerId: String = "",
    val sellerId: String = "",
    val rating: Int = 0,
    val title: String = "",
    val comment: String = ""
)