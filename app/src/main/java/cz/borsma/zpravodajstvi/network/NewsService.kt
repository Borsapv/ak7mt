package cz.borsma.zpravodajstvi.network

import cz.borsma.zpravodajstvi.network.models.TopNewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsService {
    //top news request interface
    @GET("top-headlines")
    fun getTopArticles(@Query("country") country:String): Call<TopNewsResponse>
    //category request interface
    @GET("top-headlines")
    fun getArticlesByCategories(@Query("category") category:String):Call<TopNewsResponse>
    //sources request interface
    @GET("everything")
    fun getArticlesBySources(@Query("sources") source:String):Call<TopNewsResponse>

    //search request interface
    @GET("everything")
    fun searchArticles(@Query("q") country:String):Call<TopNewsResponse>
}