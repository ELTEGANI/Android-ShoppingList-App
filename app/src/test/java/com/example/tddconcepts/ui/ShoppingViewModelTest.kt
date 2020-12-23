package com.example.tddconcepts.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.tddconcepts.MainCorotuinesRule
import com.example.tddconcepts.getOrAwaitValueTest
import com.example.tddconcepts.repositories.FakeShoppingRepository
import com.example.tddconcepts.utils.Constant
import com.example.tddconcepts.utils.Status
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ShoppingViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val corotuinesRule = MainCorotuinesRule()

    private lateinit var shoppingViewModel: ShoppingViewModel

    @Before
    fun setUp(){
      shoppingViewModel = ShoppingViewModel(FakeShoppingRepository())
    }


    @Test
    fun `insert shopping item with empty field,returns error` (){
        shoppingViewModel.insertShoppingItem("name","","3.0")

        val value = shoppingViewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with too long name field,returns error` (){
        val string = buildString {
            for (i in 1..Constant.MAX_NAME_LENGTH+1){
                append(1)
            }
        }
        shoppingViewModel.insertShoppingItem(string,"5","3.0")

        val value = shoppingViewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with too long price field,returns error` (){
        val string = buildString {
            for (i in 1..Constant.MAX_PRICE_LENGTH+1){
                append(1)
            }
        }
        shoppingViewModel.insertShoppingItem(string,"5",string)

        val value = shoppingViewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with too high amount field,returns error` (){
        val string = buildString {
            for (i in 1..Constant.MAX_PRICE_LENGTH+1){
                append(1)
            }
        }
        shoppingViewModel.insertShoppingItem(string,"599999999999999999999999999",string)

        val value = shoppingViewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with valid input,returns success` (){
        shoppingViewModel.insertShoppingItem("name","5","3.0")

        val value = shoppingViewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }

}