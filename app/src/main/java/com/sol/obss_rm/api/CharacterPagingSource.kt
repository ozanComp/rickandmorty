package com.sol.obss_rm.api

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sol.obss_rm.model.Character
import com.sol.obss_rm.api.Repository.Companion.DEFAULT_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class CharacterPagingSource(val api: Api, val options: HashMap<String, String>) :
    PagingSource<Int, Character>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val page = params.key ?: DEFAULT_PAGE_INDEX
        return try {
            options["page"] = "" + page

            Log.d("Test", "S " + options["page"])
            Log.d("Test", "S " + options["status"])

            val response = api.getCharacters(options).results
            LoadResult.Page(
                response, prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    fun test(){
        invalidate()
    }
}