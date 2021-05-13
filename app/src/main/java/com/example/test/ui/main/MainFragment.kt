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
import com.example.test.data.api.model.Entity
import com.example.test.databinding.MainFragmentBinding
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    private var _binding:MainFragmentBinding? = null

    private val binding get() = _binding!!

    private val adapter = EntityAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvEntities.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvEntities.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.searchview.setOnQueryTextListener(object:SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchview.clearFocus()
                binding.searchview.onActionViewCollapsed()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    if(it.isNotEmpty())
                        viewModel.performSearch(it)
                }
                return true
            }

        })

        viewModel.loadingState.observe(viewLifecycleOwner, Observer {
            binding.loader.visibility = it
        })

        viewModel.getEntities().observe(viewLifecycleOwner, Observer {
            adapter.setItems(it)
        })

        viewModel.info.observe(viewLifecycleOwner, Observer {
            it?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
            }
        })

        adapter.setOnEntityClickListener(object:OnEntityClickListener{
            override fun onEntityClicked(entity: Entity) {
                if(activity is MainActivity){
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