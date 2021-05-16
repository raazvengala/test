package com.example.test.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.BuildConfig
import com.example.test.data.api.RetrofitFactory
import com.example.test.data.api.model.BaseEntity
import com.example.test.data.api.model.Entity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel : ViewModel() {

    private val apiService = RetrofitFactory.create(BuildConfig.BASE_URL)

    fun getDetails(entity: BaseEntity): LiveData<Entity> {
        val mld = MutableLiveData<Entity>()

        viewModelScope.launch(Dispatchers.IO) {
            mld.postValue(apiService.getDetails(entity.Title ?: entity.imdbID
            ?: "", BuildConfig.API_KEY))
        }
        return mld
    }
}