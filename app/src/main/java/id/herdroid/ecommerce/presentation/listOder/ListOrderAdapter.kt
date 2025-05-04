package id.herdroid.ecommerce.presentation.listOder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.herdroid.ecommerce.data.local.entity.OrderEntity
import id.herdroid.ecommerce.databinding.ItemOrderBinding
import id.herdroid.ecommerce.utils.AesEncryptor
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ListOrderAdapter(
    private var orders: List<OrderEntity>,
    private val onClick: (OrderEntity) -> Unit
) : RecyclerView.Adapter<ListOrderAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(private val binding: ItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(order: OrderEntity) {
            binding.tvTotalPrice.text = "Total: $${String.format("%.2f", order.totalPrice)}"
            val formatter = SimpleDateFormat("EEEE, d MMM yyyy", Locale.ENGLISH)
            val dateText = formatter.format(Date(order.orderDate))
            binding.tvOrderDate.text = dateText
            binding.tvProductCount.text = "Items: ${order.itemCount}"
            val decryptedId = AesEncryptor.decrypt(order.orderId)
            binding.tvOrderId.text = "Order ID: #${decryptedId.take(8)}"
            binding.root.setOnClickListener { onClick(order) }

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
