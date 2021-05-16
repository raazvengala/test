package com.example.test.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.MainActivity
import com.example.test.R
import com.example.test.data.api.model.Entity
import com.example.test.data.api.model.Result
import com.example.test.databinding.ListingFragmentBinding
import com.google.android.material.snackbar.Snackbar

class ListingFragment : Fragment() {

    companion object {
        fun newInstance() = ListingFragment()
    }

    private lateinit var viewModel: ListingViewModel

    private var _binding: ListingFragmentBinding? = null

    private val binding get() = _binding!!

    private val adapter = EntityAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ListingFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListingViewModel::class.java)
        viewModel.performSearch("Movies")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvEntities.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvEntities.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.ivBack.setOnClickListener {
            if (binding.searchview.query.isNotEmpty() || !binding.searchview.isIconified) {
                binding.searchview.onActionViewCollapsed()
            } else {
                activity?.onBackPressed()
            }
        }

        binding.searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchview.clearFocus()
                binding.searchview.onActionViewCollapsed()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    if (it.isNotEmpty())
                        viewModel.performSearch(it)
                }
                return true
            }

        })



        viewModel.getEntities().observe(viewLifecycleOwner, Observer {
            when (it) {
                is Result.Loading -> binding.loader.visibility = View.VISIBLE

                is Result.Success -> {
                    binding.loader.visibility = View.GONE
                    it.data?.Search?.let {
                        adapter.setItems(it)
                    }
                }

                is Result.Error -> {
                    binding.loader.visibility = View.GONE
                    Snackbar.make(
                        binding.root, it.message
                            ?: getString(R.string.error), Snackbar.LENGTH_SHORT
                    ).show()
                }

            }
        })



        adapter.setOnEntityClickListener(object : OnEntityClickListener {
            override fun onEntityClicked(entity: Entity) {
                if (activity is MainActivity) {
                    (activity as MainActivity).moveToDetail(entity)
                }
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}