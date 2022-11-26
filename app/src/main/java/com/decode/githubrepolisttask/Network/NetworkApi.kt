package com.decode.githubrepolisttask.Network

import androidx.lifecycle.LiveData
import com.decode.githubrepolisttask.data.Repos
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.Flow

internal interface NetworkApi {

    @GET("repositories?trending")
    fun getGitRepos():Call<List<Repos>>


    @GET("repositories")
    fun searchRepo(@Query("q") query: String): Call<List<Repos>>

}