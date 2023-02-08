package com.baransolmaz.rickandmortyapp.models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.baransolmaz.rickandmortyapp.data.response.SimpleResponse
import com.baransolmaz.rickandmortyapp.data.response.SingleCharacterResponse
import com.baransolmaz.rickandmortyapp.repository.MainRepo

class DetailViewModel(//pokemon detail Viewmodel
    private val repo: MainRepo
):ViewModel() {

    private var currentPage=1
    var  characterList = mutableStateOf<List<SingleCharacterResponse>>(listOf())
    var loadErr= mutableStateOf("")
    var isLoading= mutableStateOf(false)
    var endReached= mutableStateOf(false)

    suspend fun getSingleCharacterByID(id:Int): SimpleResponse<SingleCharacterResponse> {
        return repo.getSingleCharacterByID(id)
    }


}