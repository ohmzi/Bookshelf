package com.aceage.bookshelf.network

import com.aceage.bookshelf.network.models.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface OpenLibraryApi {

  @GET("search.json")
  suspend fun searchBooks(@Query("q") query: String): Response<SearchResponse>

}