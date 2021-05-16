package com.example.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.test.data.api.model.BaseEntity
import com.example.test.databinding.MainActivityBinding
import com.example.test.ui.main.DetailsFragment
import com.example.test.ui.main.ListingFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, ListingFragment.newInstance())
                    .commitNow()
        }
    }

    fun moveToDetail(entity: BaseEntity) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, DetailsFragment.newInstance(entity))
                .addToBackStack(DetailsFragment::class.java.name)
                .commit()
    }
}