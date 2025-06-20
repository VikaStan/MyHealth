package com.example.myhealth.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myhealth.data.datasource.local.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(product: ProductEntity)

    @Query("SELECT * FROM products WHERE name LIKE :query LIMIT 1")
    suspend fun findProduct(query: String): ProductEntity?

    @Query("SELECT * FROM products WHERE id = :id")
    fun observeProduct(id: Int): Flow<ProductEntity?>
}