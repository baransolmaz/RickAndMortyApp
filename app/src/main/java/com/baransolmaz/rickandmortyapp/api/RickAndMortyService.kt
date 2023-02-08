package com.baransolmaz.rickandmortyapp.api

import com.baransolmaz.rickandmortyapp.data.response.AllCharacterResponse
import com.baransolmaz.rickandmortyapp.data.response.SingleCharacterResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyService {
    @GET ("character/{id}")
    suspend fun getCharacterByID(
        @Path("id") id: Int
    ): SingleCharacterResponse

    @GET ("character/")
    suspend fun getAllCharacters(
        @Query("page") page:Int
    ): AllCharacterResponse
}