package com.example.mvvm.network

import com.example.mvvm.model.Post
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET

interface Service {

    @GET("/posts")
    fun getPersonList(): Call<MutableList<Post>>

    @GET("/posts")
    fun getListWithRxJAVA(): Observable<List<Post>>

    @GET("/posts")
    suspend fun coGO(): MutableList<Post>

    @GET("/posts")
    fun getListWithRxNetwork(): Observable<List<Post>>
}