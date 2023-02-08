package com.baransolmaz.rickandmortyapp.repository

import com.baransolmaz.rickandmortyapp.data.response.AllCharacterResponse
import com.baransolmaz.rickandmortyapp.data.response.SimpleResponse
import com.baransolmaz.rickandmortyapp.data.response.SingleCharacterResponse

interface MainRepo {
    suspend fun getSingleCharacterByID(id:Int): SimpleResponse<SingleCharacterResponse>
    suspend fun getAllCharacters(page:Int): SimpleResponse<AllCharacterResponse>
}