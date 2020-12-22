package com.example.tddconcepts.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tddconcepts.data.local.ShoppingItem
import com.example.tddconcepts.data.remote.responses.ImageResponse
import com.example.tddconcepts.repositories.ShoppingRepository
import com.example.tddconcepts.utils.Event
import com.example.tddconcepts.utils.Resource
import kotlinx.coroutines.launch

class ShoppingViewModel @ViewModelInject constructor(
    private val shoppingRepository: ShoppingRepository
) : ViewModel(
){
   val shoppingItems = shoppingRepository.observeAllShoppingItem()
   val totalPrice    = shoppingRepository.observeTotalPrice()

    private val _images = MutableLiveData<Event<Resource<ImageResponse>>>()
    val images : LiveData<Event<Resource<ImageResponse>>> = _images

    private val _currentImageUrl = MutableLiveData<String>()
    val currentImageUrl : LiveData<String> = _currentImageUrl

    private val _insertShoppingItemStatus = MutableLiveData<Event<Resource<ShoppingItem>>>()
    val insertShoppingItemStatus : MutableLiveData<Event<Resource<ShoppingItem>>> = _insertShoppingItemStatus

    fun setCurImageUrl(url:String){
       _currentImageUrl.postValue(url)
    }

    fun deleteShoppingItem(shoppingItem: ShoppingItem) = viewModelScope.launch {
        shoppingRepository.deleteShoppingItem(shoppingItem)
    }

    fun insertShoppingItemInToDb(shoppingItem: ShoppingItem) = viewModelScope.launch {
        shoppingRepository.insertShoppingItem(shoppingItem)
    }

    fun insertShoppingItem(name:String,amount:String,price:String){

    }

    fun searchForImage(imageQuery:String){

    }
}