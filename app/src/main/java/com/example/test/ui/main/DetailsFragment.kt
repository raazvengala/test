package com.example.test.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.test.R
import com.example.test.data.api.model.BaseEntity
import com.example.test.data.api.model.Entity
import com.example.test.data.api.model.Result
import com.example.test.databinding.DetailsFragmentBinding
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class DetailsFragment : Fragment() {

    companion object {
        fun newInstance(entity: BaseEntity) = DetailsFragment().apply {
            arguments = Bundle().apply {
                putParcelable("Entity", entity)
            }
        }
    }


    private var _binding: DetailsFragmentBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: DetailsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = DetailsFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
        if (arguments?.containsKey("Entity") == true && arguments?.getParcelable<BaseEntity>("Entity") is BaseEntity) {
            val entity = arguments!!.getParcelable<BaseEntity>("Entity")
            viewModel.detailsLiveData.observe(viewLifecycleOwner, Observer {
                when (it) {
                    is Result.Loading -> binding.loader.visibility = View.VISIBLE

                    is Result.Success -> {
                        binding.loader.visibility = View.GONE
                        it.data?.let { entity ->
                            showDetails(entity)
                        }
                    }

                    is Result.Error -> {
                        binding.loader.visibility = View.GONE
                        Snackbar.make(binding.root, it.message
                                ?: getString(R.string.error), Snackbar.LENGTH_SHORT).show()
                    }
                }

            })

            viewModel.getDetails(entity!!)
        } else {
            activity?.onBackPressed()
        }

    }

    private fun showDetails(entity: Entity) {
        Picasso.get().load(entity.Poster).into(binding.ivImage)
        binding.tvDescription.text = entity.Plot
        binding.tvTitle.text = entity.Title
        if (!entity.Ratings.isNullOrEmpty()) {
            binding.tvRating.text = entity.Ratings[0].Value
        }
    }

}