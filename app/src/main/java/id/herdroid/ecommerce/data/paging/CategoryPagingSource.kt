    package id.herdroid.ecommerce.data.paging

    import android.graphics.pdf.LoadParams
    import androidx.paging.PagingSource
    import androidx.paging.PagingState
    import id.herdroid.ecommerce.data.remote.api.ProductApi
    import id.herdroid.ecommerce.domain.model.Product
    import id.herdroid.ecommerce.utils.toProduct

    class CategoryPagingSource(
        private val api: ProductApi,
        private val category: String
    ) : PagingSource<Int, Product>() {
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
            return try {
                val products = api.getProductsByCategory(category).map { it.toProduct() }
                LoadResult.Page(data = products, prevKey = null, nextKey = null)
            } catch (e: Exception) {
                LoadResult.Error(e)
            }
        }

        override fun getRefreshKey(state: PagingState<Int, Product>): Int? = null
    }
