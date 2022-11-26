package com.decode.githubrepolisttask.ViewModel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decode.githubrepolisttask.Network.NetworkApi
import com.decode.githubrepolisttask.Network.RetrofitInstance
import com.decode.githubrepolisttask.data.Repos
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyViewModel:ViewModel() {

     var liveDataReposList:MutableLiveData<List<Repos>>

    init {
        liveDataReposList= MutableLiveData()
        viewModelScope.launch(Dispatchers.IO) {
            callGetReposApi()
        }
    }

    fun getLiveDataObserver():MutableLiveData<List<Repos>>{
        return liveDataReposList
    }

    fun callGetReposApi(){
        val apiInstance=RetrofitInstance.getRetrofitInstance()
        val retrofitServiceInstance=apiInstance.create(NetworkApi::class.java)
        val call=retrofitServiceInstance.getGitRepos()

        call.enqueue(object : Callback<List<Repos>> {


            @SuppressLint("NullSafeMutableLiveData")
            override fun onFailure(call: Call<List<Repos>>, t: Throwable) {
                liveDataReposList.postValue(null)
            }

            override fun onResponse(call: Call<List<Repos>>, response: Response<List<Repos>>) {
                liveDataReposList.postValue(response.body())
            }
        })
    }
}