package com.baransolmaz.rickandmortyapp.data

data class SingleCharacterResponse(
    val created:String ="",
    val episode:List<String> = listOf(),
    val gender:String? ="Male",
    val id:Int =0,
    val location:Location =Location(),
    val name:String? ="Cult Leader Morty",
    val origin:Origin =Origin(),
    val species:String? ="Human",
    val status:String? ="Alive",
    val type:String? ="",
    val url:String? ="https://rickandmortyapi.com/api/character/84",
    val image:String?="https://rickandmortyapi.com/api/character/avatar/84.jpeg",
    var isLiked:Boolean=false
){
    data class Location(
        val name:String? ="Hideout Planet",
        val url:String =""
    )
    data class Origin(
        val name:String? ="Unknown",
        val url:String =""
    )
}
