package com.example.rickandmorty.sections.character

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmorty.retrofit.RetrofitServices
import retrofit2.HttpException
import kotlin.reflect.cast

class CharacterPageSource(
    private val  retrofitServices: RetrofitServices,
    private val query: String
): PagingSource<Int, Characters>() {

    override fun getRefreshKey(state: PagingState<Int, Characters>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Characters> {
        if (query.isEmpty()){
            return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
        }

        val page: Int = params.key ?: 1
        val pagesize: Int = params.loadSize

        val response = retrofitServices.getAllCharacters(page).execute()
        if (response.isSuccessful){
            val characters = checkNotNull(response.body()).characters.map {Characters::class.cast(it)}
            val nextKey = if (characters.size < pagesize) null else page + 1
            val prevKey = if (page == 1) null else page - 1
            return LoadResult.Page(characters, prevKey, nextKey)
        } else {
            return LoadResult.Error(HttpException(response))
        }

    }
}