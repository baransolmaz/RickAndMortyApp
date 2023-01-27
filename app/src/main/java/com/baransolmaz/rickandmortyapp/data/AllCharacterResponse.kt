package com.baransolmaz.rickandmortyapp.data


data class AllCharacterResponse(
    val info: Info =Info(),
    val results :List<SingleCharacterResponse> = listOf<SingleCharacterResponse>()
){
    data class Info(
        val count:Int =0,
        val pages:Int =0,
        val next:String="",
        val prev:String=""
    )
}
