package com.example.test.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test.BuildConfig
import com.example.test.data.api.RetrofitFactory
import com.example.test.data.api.model.EntityRepoFactory
import com.example.test.data.api.model.Result
import com.example.test.data.api.model.SearchResponse

class MainViewModel : ViewModel() {

    private val mQuery = MutableLiveData<String>()

    private val entityRepo = EntityRepoFactory.getInstance(RetrofitFactory.create(BuildConfig.BASE_URL))

    private val resultsItemsLiveData = MediatorLiveData<Result<SearchResponse?>>()

    init {
        resultsItemsLiveData.addSource(mQuery) {
            resultsItemsLiveData.addSource(entityRepo.getEntities(it, BuildConfig.API_KEY)) {
                resultsItemsLiveData.postValue(it)
            }
        }

    }

    fun getEntities(): LiveData<Result<SearchResponse?>> {
        return resultsItemsLiveData
    }

    fun performSearch(query: String) {
        if (mQuery.value != query)
            mQuery.postValue(query)
    }
}