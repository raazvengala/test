package com.example.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.test.data.api.model.BaseEntity
import com.example.test.data.api.model.Entity
import com.example.test.databinding.MainActivityBinding
import com.example.test.ui.main.DetailsFragment
import com.example.test.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding:MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
    }

    fun moveToDetail(entity: BaseEntity){
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, DetailsFragment.newInstance(entity))
                .addToBackStack(DetailsFragment::class.java.name)
                .commit()
    }
}