package com.example.booksearchapp.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.booksearchapp.databinding.FragmentFavoriteBinding
import com.example.booksearchapp.ui.adapter.BookSearchAdapter
import com.example.booksearchapp.ui.adapter.BookSearchPagingAdapter
// import com.example.booksearchapp.ui.viewmodel.BookSearchViewModel
import com.example.booksearchapp.ui.viewmodel.FavoriteViewModel
import com.example.booksearchapp.util.collectLatestStateFlow
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding
        get() = _binding!!

    // private lateinit var bookSearchViewModel: BookSearchViewModel
    // private val bookSearchViewModel by activityViewModels<BookSearchViewModel>()
    private val favoriteViewModel by viewModels<FavoriteViewModel>()

    // private lateinit var bookSearchAdapter: BookSearchAdapter
    private lateinit var bookSearchAdapter: BookSearchPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // bookSearchViewModel = (activity as MainActivity).bookSearchViewModel

        setupRecyclerView()
        setupTouchHelper(view)

        /*bookSearchViewModel.favoriteBooks.observe(viewLifecycleOwner) {
            bookSearchAdapter.submitList(it)
        }*/

        /*lifecycleScope.launch {
            bookSearchViewModel.favoriteBooks.collectLatest {
                bookSearchAdapter.submitList(it)
            }
        }*/

        /*viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                bookSearchViewModel.favoriteBooks.collectLatest {
                    bookSearchAdapter.submitList(it)
                }
            }
        }*/

        /*collectLatestStateFlow(bookSearchViewModel.favoriteBooks) {
            bookSearchAdapter.submitList(it)
        }*/
        // collectLatestStateFlow(bookSearchViewModel.favoritePagingBooks) {
        collectLatestStateFlow(favoriteViewModel.favoritePagingBooks) {
            bookSearchAdapter.submitData(it)
        }
    }

    private fun setupRecyclerView() {
        // bookSearchAdapter = BookSearchAdapter()
        bookSearchAdapter = BookSearchPagingAdapter()

        binding.rvFavoriteBooks.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            adapter = bookSearchAdapter
        }
        bookSearchAdapter.setOnItemClickListener {
            val action = FavoriteFragmentDirections.actionFragmentFavoriteToFragmentBook(it)
            findNavController().navigate(action)
        }
    }

    private fun setupTouchHelper(view: View) {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition

                /*val book = bookSearchAdapter.currentList[position]
                bookSearchViewModel.deleteBook(book)
                Snackbar.make(view, "Book has deleted", Snackbar.LENGTH_SHORT).apply {
                    setAction("Undo") {
                        bookSearchViewModel.saveBook(book)
                    }
                }.show()*/

                val pagedBook = bookSearchAdapter.peek(position)
                pagedBook?.let { book ->
                    // bookSearchViewModel.deleteBook(book)
                    favoriteViewModel.deleteBook(book)
                    Snackbar.make(view, "Book has deleted", Snackbar.LENGTH_SHORT).apply {
                        setAction("Undo") {
                            // bookSearchViewModel.saveBook(book)
                            favoriteViewModel.saveBook(book)
                        }
                    }
                }

            }
        }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvFavoriteBooks)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroy()
    }
}