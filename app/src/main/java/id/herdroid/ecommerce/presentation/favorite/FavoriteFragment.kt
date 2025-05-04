package id.herdroid.ecommerce.presentation.favorite

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import id.herdroid.ecommerce.data.local.entity.FavoriteProduct
import id.herdroid.ecommerce.databinding.FragmentFavoriteBinding
import id.herdroid.ecommerce.presentation.detail.DetailActivity
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private lateinit var adapter: FavoriteAdapter
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
        observeFavorites()
        initSwipeToDelete()
    }

    private fun setupRecyclerView() {
        adapter = FavoriteAdapter(
            onItemClick = { product ->
                val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                    putExtra("product_id", product.id)
                    putExtra("title", product.title)
                    putExtra("price", product.price)
                    putExtra("image", product.image)
                    putExtra("description", product.description)
                    putExtra("category", product.category)
                }
                startActivity(intent)
            }
        )

        binding.rvFavorites.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFavorites.adapter = adapter
    }


    private fun initSwipeToDelete() {
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(rv: RecyclerView, vh: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val favoriteProduct = adapter.currentList[viewHolder.bindingAdapterPosition]
                val favorite = FavoriteProduct.fromProduct(favoriteProduct)
                viewModel.removeFromFavorite(favorite)
                Toast.makeText(requireContext(), "Removed from favorites", Toast.LENGTH_SHORT).show()
            }
        })
        itemTouchHelper.attachToRecyclerView(binding.rvFavorites)
    }


    private fun observeFavorites() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.favorites.collect { favorites ->
                val products = favorites.map { FavoriteProduct.toProduct(it) }
                Log.d("FavoriteFragment", "Favorites: $products")
                adapter.submitList(products)
                binding.tvEmpty.visibility = if (products.isEmpty()) View.VISIBLE else View.GONE
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}