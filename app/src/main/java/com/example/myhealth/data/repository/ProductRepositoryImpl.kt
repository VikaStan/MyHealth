package com.example.myhealth.data.repository

import com.example.myhealth.data.datasource.local.ProductDao
import com.example.myhealth.data.datasource.local.entity.ProductEntity
import com.example.myhealth.data.datasource.remote.ProductService
import com.example.myhealth.domain.models.Product
import com.example.myhealth.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl @Inject constructor(
    private val service: ProductService,
    private val dao: ProductDao
) : ProductRepository {

    override suspend fun findProduct(name: String): Product? {
        val cached = dao.findProduct(name)
        if (cached != null) return cached.toDomain()
        return runCatching { service.search(name) }.getOrNull()
            ?.products?.firstOrNull()?.let { dto ->
                val product = Product(
                    id = 0,
                    name = dto.name ?: name,
                    caloriesPer100Gramm = dto.nutriments?.energy?.toInt() ?: 0,
                    proteinsPer100Gramm = dto.nutriments?.proteins?.toInt() ?: 0,
                    fatsPer100Gramm = dto.nutriments?.fat?.toInt() ?: 0,
                    carbsPer100Gramm = dto.nutriments?.carbs?.toInt() ?: 0,
                    gramms = 0,
                    caloriesPerGramm = 0,
                    description = ""
                )
                dao.upsert(ProductEntity.fromDomain(product))
                product
            }
    }

    override fun observeProduct(id: Int): Flow<Product?> =
        dao.observeProduct(id).map { it?.toDomain() }
}