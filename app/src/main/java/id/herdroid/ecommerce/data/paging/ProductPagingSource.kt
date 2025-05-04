package id.herdroid.ecommerce.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.PagingSource.LoadParams
import androidx.paging.PagingSource.LoadResult
import id.herdroid.ecommerce.data.remote.api.ProductApi
import id.herdroid.ecommerce.domain.model.Product
import id.herdroid.ecommerce.utils.toProduct

class ProductPagingSource(
    private val api: ProductApi
) : PagingSource<Int, Product>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        return try {
            val products = api.getAllProducts().map { it.toProduct() }
            LoadResult.Page(data = products, prevKey = null, nextKey = null)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? = null
}
