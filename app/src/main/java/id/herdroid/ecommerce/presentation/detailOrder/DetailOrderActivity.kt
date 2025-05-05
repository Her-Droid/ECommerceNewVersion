package id.herdroid.ecommerce.presentation.detailOrder

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import id.herdroid.ecommerce.databinding.ActivityDetailOrderBinding
import id.herdroid.ecommerce.data.local.entity.CartProductEntity
import id.herdroid.ecommerce.domain.usecase.OrderUseCase
import id.herdroid.ecommerce.presentation.checkoutSummary.CheckoutProductAdapter
import id.herdroid.ecommerce.presentation.listOder.ListOrderActivity
import id.herdroid.ecommerce.utils.AesEncryptor
import java.text.DecimalFormat
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class DetailOrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailOrderBinding

    @Inject
    lateinit var orderUseCase: OrderUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)
        binding.topAppBar.setNavigationOnClickListener {
            val intent = Intent(this, ListOrderActivity::class.java)
            startActivity(intent)
            finish()
        }
        val orderId = intent.getStringExtra("order_id") ?: return
        val decryptedId = AesEncryptor.decrypt(orderId)
        binding.tvOrderId.text = "Order ID: #${decryptedId.take(8)}"

        fetchOrderDetails(orderId)
    }

    private fun fetchOrderDetails(orderId: String) {
        lifecycleScope.launchWhenStarted {
            val order = orderUseCase.getOrderDetail(orderId)
            order?.let {
                val df = DecimalFormat("#,###.##")
                binding.tvDeliveryAddress.text = "Delivery Address: ${it.address}"
                binding.tvTotalPrice.text = "Total: $${df.format(it.totalPrice)}"
                binding.tvProductSubtotal.text = "Product Subtotal: $${df.format(it.productSubtotal)}"
                binding.tvDeliveryFee.text = "Delivery Fee: $${df.format(it.deliveryFee)}"
                binding.tvServiceFee.text = "Service Fee: $${df.format(it.serviceFee)}"
                val sdf = java.text.SimpleDateFormat("EEEE, d MMM yyyy", Locale.ENGLISH)
                val formattedDate = sdf.format(java.util.Date(it.orderDate))
                binding.tvOrderDate.text = "Order Date: $formattedDate"
                binding.tvDeliveryMethod.text = "Delivery Method: ${it.deliveryMethod}"
                binding.tvPaymentMethod.text = "Payment Method: ${it.paymentMethod}"

                val products = deserializeProductItems(it.productItems)

                updateProductList(products)
            }
        }
    }
    private fun deserializeProductItems(productItemsJson: String): List<CartProductEntity> {
        val gson = Gson()
        return gson.fromJson(productItemsJson, Array<CartProductEntity>::class.java).toList()
    }

    private fun updateProductList(products: List<CartProductEntity>) {
        val adapter = CheckoutProductAdapter()
        binding.rvOrderedProducts.adapter = adapter
        binding.rvOrderedProducts.layoutManager = LinearLayoutManager(this)
        adapter.submitList(products)
    }

    override fun onBackPressed() {
        val intent = Intent(this, ListOrderActivity::class.java)
        startActivity(intent)
        finish()
    }
}
