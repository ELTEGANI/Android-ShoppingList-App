package com.example.tddconcepts.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [ShoppingItem::class],version = 1)
abstract class ShoppingItemDataBase : RoomDatabase(){
   abstract fun shoppingDao() : ShoppingDao
}