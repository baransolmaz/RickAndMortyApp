package com.baransolmaz.rickandmortyapp.repository

import com.baransolmaz.rickandmortyapp.api.RickAndMortyService
import com.baransolmaz.rickandmortyapp.data.response.AllCharacterResponse
import com.baransolmaz.rickandmortyapp.data.response.SimpleResponse
import com.baransolmaz.rickandmortyapp.data.response.SingleCharacterResponse


class MainRepoImpl (
    private val api:RickAndMortyService
): MainRepo {

    override suspend fun getSingleCharacterByID(id: Int): SimpleResponse<SingleCharacterResponse> {
        val response = try {
            api.getCharacterByID(id)
        }catch (e:Exception){
            return SimpleResponse.Error(e.message!!)
        }
        return SimpleResponse.Success(response)
    }

    override suspend fun getAllCharacters(page:Int): SimpleResponse<AllCharacterResponse> {
        val response = try {
            api.getAllCharacters(page)
        }catch (e:Exception){
            return SimpleResponse.Error(e.message!!)
        }
        return SimpleResponse.Success(response)
    }
}