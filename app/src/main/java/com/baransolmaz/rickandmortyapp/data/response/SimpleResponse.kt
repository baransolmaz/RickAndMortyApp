package com.baransolmaz.rickandmortyapp.data.response

sealed class SimpleResponse<T>(
    val data :T?=null,
    val message:String?=null
){
    class Success<T>(data:T): SimpleResponse<T>(data)
    class Error<T>(message: String, data: T?=null): SimpleResponse<T>(data,message)
    class Loading<T> : SimpleResponse<T>()
}
