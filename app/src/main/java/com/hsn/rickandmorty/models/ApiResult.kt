package com.hsn.rickandmorty.models

sealed class ApiResult<T>(val data : T? =null, val message : String? =null){
    class Success<T>(data: T?) : ApiResult<T>(data)
    class Error<T>(message: String?) : ApiResult<T>(null,message)
    class Loading<T>(val isLoading : Boolean = true) : ApiResult<T>(null,null)
}