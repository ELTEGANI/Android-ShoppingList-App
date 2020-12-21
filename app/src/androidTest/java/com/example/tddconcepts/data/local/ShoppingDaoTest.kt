package com.example.tddconcepts.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.tddconcepts.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@SmallTest
class ShoppingDaoTest {

   @get: Rule
   var instantTaskExecutorRule = InstantTaskExecutorRule()

   private lateinit var shoppingItemDataBase: ShoppingItemDataBase
   private lateinit var shoppingDao: ShoppingDao

   @Before
   fun setUp(){
     shoppingItemDataBase = Room.inMemoryDatabaseBuilder(
         ApplicationProvider.getApplicationContext(),ShoppingItemDataBase::class.java
     ).allowMainThreadQueries().build()
       shoppingDao = shoppingItemDataBase.shoppingDao()
   }

    
    @After
    fun tearDown(){
        shoppingItemDataBase.close()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun insertShoppingItem() = runBlockingTest {
       val shoppingItem = ShoppingItem("iPhone",100,40f,"dss",id = 1)
       shoppingDao.insertShoppingItem(shoppingItem)
       val allShoppingItem = shoppingDao.observeAllShoppingItem().getOrAwaitValue()
       assertThat(allShoppingItem).contains(shoppingItem)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun deleteShoppingItem() = runBlockingTest {
        val shoppingItem = ShoppingItem("iPhone",100,40f,"dss",id = 1)
        shoppingDao.insertShoppingItem(shoppingItem)
        shoppingDao.deleteShoppingItem(shoppingItem)

        val allShoppingItems =  shoppingDao.observeAllShoppingItem().getOrAwaitValue()

        assertThat(allShoppingItems).doesNotContain(shoppingItem)

    }

    @ExperimentalCoroutinesApi
    @Test
    fun observeTotalPriceSum() = runBlockingTest {
        val shoppingItem1 = ShoppingItem("iPhone",2,10f,"dss",id = 1)
        val shoppingItem2 = ShoppingItem("iPhone",4,5.5f,"dss",id = 2)
        val shoppingItem3 = ShoppingItem("iPhone",0,100f,"dss",id = 3)
        shoppingDao.insertShoppingItem(shoppingItem1)
        shoppingDao.insertShoppingItem(shoppingItem2)
        shoppingDao.insertShoppingItem(shoppingItem3)

        val totalPriceSum = shoppingDao.observeTotalPrice().getOrAwaitValue()

        assertThat(totalPriceSum).isEqualTo(2 * 10f + 4 * 5.5f)

    }
}