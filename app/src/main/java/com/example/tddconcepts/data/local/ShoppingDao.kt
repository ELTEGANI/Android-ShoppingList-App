package com.example.tddconcepts.data.local

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface ShoppingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    @Delete
    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    @Query("select * from shopping_items")
    fun observeAllShoppingItem(shoppingItem: ShoppingItem):LiveData<List<ShoppingItem>>

    @Query("select SUM(price * amount) from shopping_items")
    fun observeTotalPrice():LiveData<Float>
}