package com.example.test.ui.main

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.BuildConfig
import com.example.test.data.api.RetrofitFactory
import com.example.test.data.api.model.Entity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val loadingState = MutableLiveData<Int>()
    private val apiService = RetrofitFactory.create(BuildConfig.BASE_URL)
    private val resultsItemsLiveData = MutableLiveData<List<Entity>>()

    private var searchJob: Job? = null

    fun getEntities():LiveData<List<Entity>>{
        return resultsItemsLiveData
    }

    fun performSearch(query: String){
        searchJob?.cancel()
        searchJob = viewModelScope.launch(Dispatchers.IO){
            delay(500)
            loadingState.postValue(View.VISIBLE)
            kotlin.runCatching {
                resultsItemsLiveData.postValue(apiService.getMovies(query, BuildConfig.API_KEY)?.Search?: emptyList())
            }.onFailure {
                resultsItemsLiveData.postValue(emptyList())
            }
            loadingState.postValue(View.GONE)
        }

    }
}