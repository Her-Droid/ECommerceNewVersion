package id.herdroid.ecommerce.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint
import id.herdroid.ecommerce.databinding.FragmentHomeBinding
import id.herdroid.ecommerce.presentation.cart.CartActivity
import id.herdroid.ecommerce.presentation.detail.DetailActivity
import id.herdroid.ecommerce.presentation.shimmer.ShimmerAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var shimmerAdapter: ShimmerAdapter

    private val productAdapter by lazy {
        ProductListAdapter(
            onAddToCartClick = { product ->
                showLoading(true)
                viewModel.addToCart(product.id) {
                    showLoading(false)
                    Toast.makeText(requireContext(), "Added to cart!", Toast.LENGTH_SHORT).show()
                }
            },
            onItemClick = { product ->
                val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                    putExtra("product_id", product.id)
                    putExtra("title", product.title)
                    putExtra("price", product.price)
                    putExtra("description", product.description)
                    putExtra("image", product.image)
                    putExtra("category", product.category)
                }
                startActivity(intent)
            }
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        shimmerAdapter = ShimmerAdapter()
        binding.rvShimmer.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvShimmer.adapter = shimmerAdapter

        binding.rvProduct.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvProduct.adapter = productAdapter

        categoryAdapter = CategoryAdapter { selected ->
            viewModel.filterByCategory(selected)
            categoryAdapter.setSelectedCategory(selected)
            showShimmer(true)
        }

        binding.rvCategory.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvCategory.adapter = categoryAdapter

        binding.ivCart.setOnClickListener {
            startActivity(Intent(requireContext(), CartActivity::class.java))
        }

        viewModel.categories.observe(viewLifecycleOwner) {
            categoryAdapter.submitList(it)
            categoryAdapter.setSelectedCategory("All")
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.products.collectLatest { pagingData ->
                productAdapter.submitData(pagingData)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            productAdapter.loadStateFlow.collectLatest { loadState ->
                val isLoading = loadState.refresh is LoadState.Loading
                showShimmer(isLoading)
            }
        }

        viewModel.username.observe(viewLifecycleOwner) { name ->
            binding.tvGreeting.text = "Hi, $name"
        }

        viewModel.profileImage.observe(viewLifecycleOwner) { imageUri ->
            if (!imageUri.isNullOrEmpty()) {
                Glide.with(this)
                    .load(imageUri)
                    .apply(RequestOptions().transform(CircleCrop()))
                    .into(binding.ivProfile)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
        productAdapter.setLoading(isLoading)
    }

    private fun showShimmer(show: Boolean) {
        binding.shimmerLayout.isVisible = show
        binding.rvShimmer.isVisible = show
        binding.rvProduct.isVisible = !show

        if (show) binding.shimmerLayout.startShimmer() else binding.shimmerLayout.stopShimmer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
