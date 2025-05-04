package id.herdroid.ecommerce.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.herdroid.ecommerce.databinding.ItemProductBinding
import id.herdroid.ecommerce.domain.model.Product

class ProductListAdapter(
    private val onAddToCartClick: (Product) -> Unit,
    private val onItemClick: (Product) -> Unit,
    private var isLoading: Boolean = false // Add a variable to track loading state
) : PagingDataAdapter<Product, ProductListAdapter.ProductViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Product, newItem: Product) = oldItem == newItem
        }
    }

    inner class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            Glide.with(binding.ivImage.context)
                .load(product.image)
                .centerCrop()
                .into(binding.ivImage)

            binding.tvTitle.text = product.title
            binding.tvCategory.text = product.category
            binding.tvPrice.text = "$${product.price}"

            // Disable the button if loading is in progress
            binding.ivAddToCart.isEnabled = !isLoading

            binding.ivAddToCart.setOnClickListener {
                onAddToCartClick(product)
            }

            binding.root.setOnClickListener {
                onItemClick(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ProductViewHolder(
        ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    fun setLoading(isLoading: Boolean) {
        this.isLoading = isLoading
        notifyDataSetChanged()
    }
}

