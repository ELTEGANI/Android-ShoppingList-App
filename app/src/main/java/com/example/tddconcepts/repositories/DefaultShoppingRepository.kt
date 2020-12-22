package com.example.tddconcepts.repositories

import androidx.lifecycle.LiveData
import com.example.tddconcepts.data.local.ShoppingDao
import com.example.tddconcepts.data.local.ShoppingItem
import com.example.tddconcepts.data.remote.PixalByApi
import com.example.tddconcepts.data.remote.responses.ImageResponse
import com.example.tddconcepts.utils.Resource
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject


class DefaultShoppingRepository @Inject constructor(
    private val shoppingDao: ShoppingDao,
    private val pixalByApi: PixalByApi
):ShoppingRepository{
    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.insertShoppingItem(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.deleteShoppingItem(shoppingItem)
    }

    override fun observeAllShoppingItem(shoppingItem: ShoppingItem): LiveData<List<ShoppingItem>> {
       return shoppingDao.observeAllShoppingItem()
    }

    override fun observeTotalPrice(): LiveData<Float> {
     return shoppingDao.observeTotalPrice()
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
       return try {
          val response = pixalByApi.searchForImage(imageQuery)
           if(response.isSuccessful){
               response.body()?.let {
                   return@let Resource.success(it)
               }?:Resource.error("A unknown Error Occured",null)
           }else{
               Resource.error("A unknown Error Occured",null)
           }
       }catch (e:Exception){
          Resource.error("Couldnt Reach The server check your Internet connection",null)
       }
    }
}