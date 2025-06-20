package com.example.myhealth.domain.repository

import com.example.myhealth.domain.models.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun findProduct(name: String): Product?
    fun observeProduct(id: Int): Flow<Product?>
}