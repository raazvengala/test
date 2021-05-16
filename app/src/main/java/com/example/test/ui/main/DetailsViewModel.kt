package com.example.test.ui.main

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.test.BuildConfig
import com.example.test.data.api.RetrofitFactory
import com.example.test.data.api.model.BaseEntity
import com.example.test.data.api.model.Entity
import com.example.test.data.EntityRepoFactory
import com.example.test.data.api.model.Result

class DetailsViewModel : ViewModel() {

    private val entityRepo =
        EntityRepoFactory.getInstance(RetrofitFactory.create(BuildConfig.BASE_URL))

    val detailsLiveData = MediatorLiveData<Result<Entity?>>()

    fun getDetails(entity: BaseEntity) {
        detailsLiveData.addSource(
            entityRepo.getDetails(
                entity.Title ?: entity.imdbID
                ?: "", BuildConfig.API_KEY
            )
        ) {
            detailsLiveData.postValue(it)
        }
    }
}