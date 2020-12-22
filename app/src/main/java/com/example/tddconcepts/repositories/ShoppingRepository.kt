package com.example.tddconcepts.repositories

import androidx.lifecycle.LiveData
import com.example.tddconcepts.data.local.ShoppingItem
import com.example.tddconcepts.data.remote.responses.ImageResponse
import com.example.tddconcepts.utils.Resource
import retrofit2.Response


interface ShoppingRepository {
    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun observeAllShoppingItem(shoppingItem: ShoppingItem):LiveData<List<ShoppingItem>>

    fun observeTotalPrice():LiveData<Float>

    suspend fun searchForImage(imageQuery:String):Resource<ImageResponse>
}