package com.baransolmaz.rickandmortyapp.models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baransolmaz.rickandmortyapp.data.response.SimpleResponse
import com.baransolmaz.rickandmortyapp.data.response.SingleCharacterResponse
import com.baransolmaz.rickandmortyapp.repository.MainRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val repo: MainRepo
):ViewModel() {
    private var currentPage=1
    var  characterList = mutableStateOf<List<SingleCharacterResponse>>(listOf())
    var likedList= mutableStateOf<List<SingleCharacterResponse>>(listOf())

    var loadErr= mutableStateOf("")
    var isLoading= mutableStateOf(false)
    var endReached= mutableStateOf(false)

    var isSearching= mutableStateOf(false)
    private var isSearchStarting=true
    private var cachedList= listOf<SingleCharacterResponse>()

    val isFiltering= mutableStateOf(false)
    private var isFilterStarting=true
    private var cachedLiked= listOf<SingleCharacterResponse>()

    init {
        loadPage()
    }
    fun searchCharacter(query:String) {
        val listToSearch = if (isSearchStarting){
                characterList.value
            }else{
                cachedList
            }
        viewModelScope.launch (Dispatchers.Default){
            if (query.isEmpty()){
                characterList.value=cachedList
                isSearching.value=false
                isSearchStarting=true
                return@launch
            }
            val result=listToSearch.filter {
                it.name!!.contains(query.trim(),ignoreCase = true)||it.id.toString()==query.trim()
            }
            if (isSearchStarting){
                cachedList=characterList.value
                isSearchStarting=false
            }
            characterList.value=result
            isSearching.value=true
        }
    }
    fun filterCharacters(filter:String,isFilter:Boolean) {

        val listToSearch = if (isFilterStarting){
            likedList.value
        }else{
            cachedLiked
        }
        viewModelScope.launch (Dispatchers.Default){
            if (filter.isEmpty() || filter == "Both" || !isFilter){
                likedList.value=cachedLiked
                isFiltering.value=false
                isFilterStarting=true
                return@launch
            }
            val result=listToSearch.filter {
                it.status!! == filter
            }
            if (isFilterStarting){
                cachedLiked=likedList.value
                isFilterStarting=false
            }
            likedList.value=result
            isFiltering.value=true
        }
    }
    fun loadPage(){
        viewModelScope.launch {
            isLoading.value=true
            when(val response=repo.getAllCharacters(currentPage)){
                is SimpleResponse.Success->{
                    endReached.value=currentPage >= response.data!!.info.pages
                    val characters=response.data.results.mapIndexed{ _, entry->
                        entry
                    }
                    currentPage++
                    loadErr.value=""
                    isLoading.value=false
                    characterList.value+=characters
                }
                is SimpleResponse.Error->{
                    loadErr.value= response.message!!
                    isLoading.value=false
                }
                else -> {

                }
            }
        }
    }
    fun likedCharacters(){
        viewModelScope.launch (Dispatchers.Default){
            likedList.value=characterList.value.filter{
                it.isLiked
            }
        }
    }
    fun order(type:Int){
        viewModelScope.launch {
            likedList.value=likedList.value.sortedBy {
                it.episode.size * type
            }

        }
    }
}