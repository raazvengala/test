package com.example.test.data.api.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.test.data.api.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object EntityRepoFactory {
    private var instance: EntityRepo? = null

    fun getInstance(apiService: ApiService): EntityRepo {
        if (instance == null) {
            instance = EntityRepoImpl(apiService)
        }
        return instance!!
    }


    private class EntityRepoImpl(private val apiService: ApiService) : EntityRepo {

        override fun getEntities(query: String, apiKey: String): LiveData<Result<SearchResponse?>> {
            val resource = MutableLiveData<Result<SearchResponse?>>()
            resource.postValue(Result.Loading(null))

            apiService.getEntities(query, apiKey).enqueue(object : Callback<SearchResponse?> {
                override fun onFailure(call: Call<SearchResponse?>, t: Throwable) {
                    resource.postValue(Result.Error(t.message ?: "", null))
                }

                override fun onResponse(call: Call<SearchResponse?>, response: Response<SearchResponse?>) {
                    if (response.isSuccessful && response.body() != null) {
                        if (response.body()!!.Response == "True") {
                            resource.postValue(Result.Success(response.body()))
                        } else {
                            resource.postValue(Result.Error(response.body()!!.Error ?: ""))
                        }
                    } else {
                        resource.postValue(Result.Error(response.message(), null))
                    }
                }

            })


            return resource
        }

        override fun getDetails(query: String, apiKey: String): LiveData<Result<Entity?>> {
            val resource = MutableLiveData<Result<Entity?>>()
            resource.postValue(Result.Loading(null))
            apiService.getDetails(query, apiKey).enqueue(object : Callback<Entity?> {
                override fun onFailure(call: Call<Entity?>, t: Throwable) {
                    resource.postValue(Result.Error(t.message ?: "", null))
                }

                override fun onResponse(call: Call<Entity?>, response: Response<Entity?>) {
                    if (response.isSuccessful) {
                        resource.postValue(Result.Success(response.body()))
                    } else {
                        resource.postValue(Result.Error(response.message(), null))
                    }
                }

            })

            return resource
        }
    }

    interface EntityRepo {
        fun getEntities(query: String, apiKey: String): LiveData<Result<SearchResponse?>>
        fun getDetails(query: String, apiKey: String): LiveData<Result<Entity?>>
    }
}