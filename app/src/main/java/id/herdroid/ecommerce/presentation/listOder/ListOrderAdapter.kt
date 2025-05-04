package id.herdroid.ecommerce.presentation.listOder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.herdroid.ecommerce.data.local.entity.OrderEntity
import id.herdroid.ecommerce.databinding.ItemOrderBinding

class ListOrderAdapter(
    private var orders: List<OrderEntity>,
    private val onClick: (OrderEntity) -> Unit
) : RecyclerView.Adapter<ListOrderAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(private val binding: ItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(order: OrderEntity) {
            binding.tvTotalPrice.text = "Total: $${order.totalPrice}"
            binding.tvOrderDate.text = "Date: ${formatDate(order.orderDate)}"
            binding.tvProductCount.text = "Items: ${order.itemCount}"
            binding.root.setOnClickListener { onClick(order) }
        }

        private fun formatDate(timestamp: Long): String {
            val sdf = java.text.SimpleDateFormat("dd MMM yyyy", java.util.Locale.getDefault())
            return sdf.format(java.util.Date(timestamp))
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(orders[position])
    }

    override fun getItemCount(): Int = orders.size

    fun updateData(newOrders: List<OrderEntity>) {
        this.orders = newOrders
        notifyDataSetChanged()
    }

}
