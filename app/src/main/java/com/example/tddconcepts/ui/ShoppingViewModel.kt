package com.example.tddconcepts.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tddconcepts.data.local.ShoppingItem
import com.example.tddconcepts.data.remote.responses.ImageResponse
import com.example.tddconcepts.repositories.ShoppingRepository
import com.example.tddconcepts.utils.Constant
import com.example.tddconcepts.utils.Event
import com.example.tddconcepts.utils.Resource
import kotlinx.coroutines.launch
import java.lang.Exception

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
      if(name.isEmpty() || amount.isEmpty() || price.isEmpty()){
          _insertShoppingItemStatus.postValue(Event(Resource.error("Fields Must Not Be Empty",null)))
          return
      }
     if (name.length > Constant.MAX_NAME_LENGTH){
         _insertShoppingItemStatus.postValue(Event(Resource.error("The Name of The Item must not"+
             "must not exceed ${Constant.MAX_NAME_LENGTH} characters",null)))
         return
     }
    if (price.length > Constant.MAX_PRICE_LENGTH){
            _insertShoppingItemStatus.postValue(Event(Resource.error("The Price of The Item must not"+
                    "must not exceed ${Constant.MAX_PRICE_LENGTH} characters",null)))
            return
    }
     val amountString = try {
         amount.toInt()
     }catch (e:Exception){
         _insertShoppingItemStatus.postValue(Event(Resource.error("Please Enter Avalid Amount",null)))
        return
     }
        val shopping = ShoppingItem(name,amountString,price.toFloat(),_currentImageUrl.value?:"")
        insertShoppingItemInToDb(shopping)
        setCurImageUrl("")
        _insertShoppingItemStatus.postValue(Event(Resource.success(shopping)))
    }

    fun searchForImage(imageQuery:String){
      if(imageQuery.isEmpty()){
          return
      }
        _images.value = Event(Resource.loading(null))
        viewModelScope.launch {
            val response = shoppingRepository.searchForImage(imageQuery)
            _images.value = Event(response)
        }
    }

}