package com.example.tddconcepts.di

import android.content.Context
import androidx.room.Room
import com.example.tddconcepts.data.local.ShoppingItem
import com.example.tddconcepts.data.local.ShoppingItemDataBase
import com.example.tddconcepts.data.remote.PixalByApi
import com.example.tddconcepts.utils.Constant.BASE_URL
import com.example.tddconcepts.utils.Constant.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideShoppingItemDataBase(@ApplicationContext context:Context) =
            Room.databaseBuilder(context,ShoppingItemDataBase::class.java,DATABASE_NAME).build()


    @Singleton
    @Provides
    fun provideShoppingDao(shoppingItemDataBase: ShoppingItemDataBase)=shoppingItemDataBase.shoppingDao()


    @Singleton
    @Provides
    fun providePixalByApi():PixalByApi{
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
                .create(PixalByApi::class.java)
    }
}