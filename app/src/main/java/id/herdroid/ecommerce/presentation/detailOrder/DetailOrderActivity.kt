package id.herdroid.ecommerce.presentation.detailOrder

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import id.herdroid.ecommerce.databinding.ActivityDetailOrderBinding
import id.herdroid.ecommerce.presentation.listOder.ListOrderViewModel

@AndroidEntryPoint
class DetailOrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailOrderBinding
    private val viewModel: ListOrderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val orderId = intent.getIntExtra("order_id", -1)
//        if (orderId != -1) {
//            viewModel.fetchOrderById(orderId)
//        }
//
//        lifecycleScope.launchWhenStarted {
//            viewModel.selectedOrder.collect { order ->
//                order?.let {
//                    binding.tvOrderId.text = "Order #${it.id}"
//                    binding.tvTotal.text = "Total: $${it.totalPrice}"
//                    binding.tvAddress.text = "Address: ${it.shippingAddress}"
//                    // Tambahkan field lain sesuai kebutuhan UI
//                }
//            }
//        }
    }
}
