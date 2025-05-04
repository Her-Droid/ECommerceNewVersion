package id.herdroid.ecommerce.presentation.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.herdroid.ecommerce.databinding.ItemProductBinding
import id.herdroid.ecommerce.domain.model.Product

class FavoriteAdapter(
    private val onItemClick: (Product) -> Unit
) : ListAdapter<Product, FavoriteAdapter.FavViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Product, newItem: Product) = oldItem == newItem
        }
    }

    inner class FavViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            Glide.with(binding.ivImage.context)
                .load(product.image)
                .centerCrop()
                .into(binding.ivImage)

            binding.tvTitle.text = product.title
            binding.tvCategory.text = product.category
            binding.tvPrice.text = "$${product.price}"

            binding.root.setOnClickListener {
                onItemClick(product)
            }

            binding.ivAddToCart.visibility = View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FavViewHolder(
        ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
