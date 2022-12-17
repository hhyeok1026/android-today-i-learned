package com.example.booksearchapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.example.booksearchapp.databinding.ItemLoadStateBinding

class BookSearchLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<BookSearchLoadStateViewHoler>() {

    override fun onBindViewHolder(holder: BookSearchLoadStateViewHoler, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): BookSearchLoadStateViewHoler {
        return BookSearchLoadStateViewHoler(
            ItemLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            retry
        )
    }

}