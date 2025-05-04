package id.herdroid.ecommerce.presentation.checkoutSummary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.herdroid.ecommerce.data.local.entity.CartProductEntity
import id.herdroid.ecommerce.databinding.ItemCheckoutProductBinding

class CheckoutProductAdapter : ListAdapter<CartProductEntity, CheckoutProductAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<CartProductEntity>() {
        override fun areItemsTheSame(old: CartProductEntity, new: CartProductEntity) = old.id == new.id
        override fun areContentsTheSame(old: CartProductEntity, new: CartProductEntity) = old == new
    }
) {
    inner class ViewHolder(val binding: ItemCheckoutProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: CartProductEntity) {
            binding.tvTitle.text = product.title
            binding.tvPrice.text = "$${String.format("%.2f", product.price)} x ${product.quantity}"
            Glide.with(binding.ivProduct).load(product.image).into(binding.ivProduct)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCheckoutProductBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
