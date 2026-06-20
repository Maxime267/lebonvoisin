package com.example.lebonvoisin.dataclass.review

data class ReviewUI(
    val ownerName: String,
    val sellerName: String = "",
    var ownerId: String = "",
    val sellerId: String = "",
    val rating: Int = 0,
    val title: String = "",
    val comment: String= ""
)