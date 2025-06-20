package com.example.myhealth.data.datasource.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface ProductService {
    @GET("cgi/search.pl?search_simple=1&action=process&json=1")
    suspend fun search(@Query("search_terms") query: String): SearchResponse
}