package com.example.booksearchapp.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.booksearchapp.data.model.Book
import com.example.booksearchapp.databinding.ItemBookPreviewBinding

class BookSearchViewHolder(
    private val binding: ItemBookPreviewBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(book: Book) {
        val author = book.authors.toString().removeSurrounding("[","]")
        val publisher = book.publisher

        // subString에서 버그생김.   java.lang.StringIndexOutOfBoundsException: begin 0, end 10, length 0
        // isEmpty로 잘못가져와서 생기는 버그.. 날짜도 표시가 안되고 있었다.
        // val date = if (book.datetime.isEmpty()) book.datetime.substring(0, 10) else ""
        val date = if (book.datetime.isNotEmpty()) book.datetime.substring(0, 10) else ""

        itemView.apply {
            binding.ivArticleImage.load(book.thumbnail)
            binding.tvTitle.text = book.title
            binding.tvAuthor.text = "$author | $publisher"
            binding.tvDatetime.text = date
        }
    }
}