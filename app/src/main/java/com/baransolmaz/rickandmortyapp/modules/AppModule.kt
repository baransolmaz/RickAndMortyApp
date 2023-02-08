package com.baransolmaz.rickandmortyapp.modules

import com.baransolmaz.rickandmortyapp.models.MainViewModel
import com.baransolmaz.rickandmortyapp.api.RickAndMortyService
import com.baransolmaz.rickandmortyapp.models.DetailViewModel
import com.baransolmaz.rickandmortyapp.repository.MainRepo
import com.baransolmaz.rickandmortyapp.repository.MainRepoImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()))
            .build()
            .create(RickAndMortyService::class.java)
    }
    single<MainRepo> {
        MainRepoImpl(get())
    }
    viewModel{
        MainViewModel(get())
    }
    viewModel{
        DetailViewModel(get())
    }

}