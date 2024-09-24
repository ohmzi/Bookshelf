package com.aceage.bookshelf.network.models

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class SearchResponse(
    val docs: List<Book>
)

@JsonClass(generateAdapter = true)
data class Book(
    val key: String,
    val cover_i: Long?,
    val title: String,
    val author_name: List<String>?,
    val first_publish_year: Int?
)