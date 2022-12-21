package com.example.booksearchapp.ui.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.booksearchapp.R
import com.example.booksearchapp.databinding.FragmentSettingsBinding
// import com.example.booksearchapp.ui.viewmodel.BookSearchViewModel
import com.example.booksearchapp.ui.viewmodel.SettingsViewModel
import com.example.booksearchapp.util.Sort
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding
        get() = _binding!!

    // private lateinit var bookSearchViewModel: BookSearchViewModel
    // private val bookSearchViewModel by activityViewModels<BookSearchViewModel>()
    private val settingsViewModel by viewModels<SettingsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // bookSearchViewModel = (activity as MainActivity).bookSearchViewModel

        saveSettings()
        loadSettings()
        showWorkStatus()
    }

    // 리스너 등록
    private fun saveSettings() {
        binding.rgSort.setOnCheckedChangeListener { _, checkedId ->
            val value = when (checkedId) {
                R.id.rb_accuracy -> Sort.ACCURACY.value
                R.id.rb_latest -> Sort.LATEST.value
                else -> return@setOnCheckedChangeListener
            }
            // bookSearchViewModel.saveSortMode(value)
            settingsViewModel.saveSortMode(value)
        }

        binding.swCacheDelete.setOnCheckedChangeListener { _, isChecked ->
            // bookSearchViewModel.saveCacheDeleteMode(isChecked)
            settingsViewModel.saveCacheDeleteMode(isChecked)
            if (isChecked) {
                // bookSearchViewModel.setWork()
                settingsViewModel.setWork()
            } else {
                // bookSearchViewModel.deleteWork()
                settingsViewModel.deleteWork()
            }
        }
    }

    private fun loadSettings() {
        lifecycleScope.launch {
            // val buttonId = when (bookSearchViewModel.getSortMode()) {
            val buttonId = when (settingsViewModel.getSortMode()) {
                Sort.ACCURACY.value -> R.id.rb_accuracy
                Sort.LATEST.value -> R.id.rb_latest
                else -> return@launch
            }
            binding.rgSort.check(buttonId)
        }

        // WorkManager
        lifecycleScope.launch {
            // val mode = bookSearchViewModel.getCacheDeleteMode()
            val mode = settingsViewModel.getCacheDeleteMode()
            binding.swCacheDelete.isChecked = mode
        }
    }

    private fun showWorkStatus() {
        // bookSearchViewModel.getWorkStatus().observe(viewLifecycleOwner) { workInfo ->
        settingsViewModel.getWorkStatus().observe(viewLifecycleOwner) { workInfo ->
            Log.d("WorkManagerLog", workInfo.toString())
            if (workInfo.isEmpty()) {
                binding.tvWorkStatus.text = "No works"
            } else {
                binding.tvWorkStatus.text = workInfo[0].state.toString()
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}