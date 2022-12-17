package com.example.booksearchapp.data.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.booksearchapp.data.api.RetrofitInstance.api
import com.example.booksearchapp.data.model.Book
import com.example.booksearchapp.util.Constants.PAGING_SIZE
import retrofit2.HttpException
import java.io.IOException

class BookSearchPagingSource(
    private val query: String,
    private val sort: String,
) : PagingSource<Int, Book>() {

    init {
        // Log.d("PagingLog", "BookSearchPagingSource - init - 검색을 시도했다.")
    }

    override fun getRefreshKey(state: PagingState<Int, Book>): Int? {
        // Log.d("PagingLog", "BookSearchPagingSource - getRefreshKey - 검색을 시도했다.")
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Book> {
        // Log.d("PagingLog", "BookSearchPagingSource - load - 검색을 시도했다.")
        // Log.d("PagingLog", "BookSearchPagingSource - load - STARTING_PAGE_INDEX: $STARTING_PAGE_INDEX")
        // 검색할때,
        // next로 넘어가는 pageNum은 1씩 증가.
        // prekey로 넘어가는 pageNum은 -3씩인듯?.. 하 모르겠다. // 이게 maxSize랑 연관이 있을듯한데. maxSize올려도 -3.. 으로 했다가 -1씩 되네.
        return try {
            val pageNumber = params.key ?: STARTING_PAGE_INDEX
            //Log.d("PagingLog", "BookSearchPagingSource - load - pageNumber: $pageNumber")

            val response = api.searchBooks(query, sort, pageNumber, params.loadSize)
            val endOfPaginationReached = response.body()?.meta?.isEnd!!

            val data = response.body()?.documents!!
            val prevKey = if (pageNumber == STARTING_PAGE_INDEX) null else pageNumber - 1
            val nextKey = if (endOfPaginationReached) {
                null
            } else {
                pageNumber + (params.loadSize / PAGING_SIZE)
            }
            //Log.d("PagingLog", "BookSearchPagingSource - load - prevKey: $prevKey")
            //Log.d("PagingLog", "BookSearchPagingSource - load - nextKey: $nextKey")
            LoadResult.Page(
                data = data,
                prevKey = prevKey,
                nextKey = nextKey,
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    companion object {
        const val STARTING_PAGE_INDEX = 1
    }
}