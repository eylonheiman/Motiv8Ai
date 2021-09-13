package com.eylonheimann.motiv8ai.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.eylonheimann.motiv8ai.databinding.HomeFragmentBinding
import com.eylonheimann.motiv8ai.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    companion object {
        private const val TAG = "HomeFragment"
    }

    private val viewModel: HomeViewModel by viewModels()
    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)

        lifecycleScope.launch() {
            viewModel.items.collect() {
                Log.e(HomeViewModel.TAG, "collect and emit $it")
                Log.e("D", it.name)
                binding.text.text = it.name
            }
        }
        initRecycler()

        binding.startBtn.setOnClickListener {
            initRecycler()
            viewModel.startStreamItems()
        }

        binding.stopBtn.setOnClickListener {
            Log.d(TAG, "stopBtn")
            viewModel.stopSteamItems()
        }
        return binding.root
    }

    private fun initRecycler() {
        var addapter = CustomAdapter(viewModel.getSavedItems().toTypedArray())
        binding.recycler.apply {
            adapter = addapter
        }
    }
}