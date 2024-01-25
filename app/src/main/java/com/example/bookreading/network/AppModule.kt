package com.example.bookreading.network


import com.example.bookreading.repository.BookRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun bookRepo(bookApi: BookApi) = BookRepository(bookApi)

    @Singleton
    @Provides
    fun bookApi() : BookApi{
      return  Retrofit.Builder()
            .baseUrl(Constant.Base_Url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BookApi::class.java)
    }
}