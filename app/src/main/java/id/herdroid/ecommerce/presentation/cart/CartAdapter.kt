package id.herdroid.ecommerce.presentation.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.herdroid.ecommerce.R
import id.herdroid.ecommerce.data.local.entity.CartProductEntity
import id.herdroid.ecommerce.databinding.ItemCartBinding

class CartAdapter(
    private val onDelete: (CartProductEntity) -> Unit,
    private val onQuantityChange: (CartProductEntity) -> Unit
) : ListAdapter<CartProductEntity, CartAdapter.CartViewHolder>(DiffCallback()) {

    inner class CartViewHolder(val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CartProductEntity) = with(binding) {
            tvTitle.text = item.title
            tvPrice.text = "$${item.price}"
            tvQuantity.text = item.quantity.toString()
            Glide.with(ivImage).load(item.image).into(ivImage)

            btnMinus.setImageResource(
                if (item.quantity > 1) R.drawable.ic_minus_light else R.drawable.ic_minus_dark
            )

            btnPlus.setOnClickListener {
                val updatedItem = item.copy(quantity = item.quantity + 1)
                onQuantityChange(updatedItem)
            }

            btnMinus.setOnClickListener {
                if (item.quantity > 1) {
                    val updatedItem = item.copy(quantity = item.quantity - 1)
                    onQuantityChange(updatedItem)
                }
            }

            btnDelete.setOnClickListener {
                onDelete(item)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<CartProductEntity>() {
        override fun areItemsTheSame(oldItem: CartProductEntity, newItem: CartProductEntity) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: CartProductEntity, newItem: CartProductEntity) = oldItem == newItem
    }
}
