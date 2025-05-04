package id.herdroid.ecommerce.presentation.listOder

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.herdroid.ecommerce.databinding.ActivityListOrderBinding
import id.herdroid.ecommerce.presentation.detailOrder.DetailOrderActivity

//import id.herdroid.ecommerce.presentation.detailOrder.DetailOrderActivity

@AndroidEntryPoint
class ListOrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListOrderBinding
    private val viewModel: ListOrderViewModel by viewModels()
    private lateinit var adapter: ListOrderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ListOrderAdapter(emptyList()) { order ->
            // Handle item click
            val intent = Intent(this, DetailOrderActivity::class.java).apply {
                putExtra("order_id", order.orderId)
            }
            startActivity(intent)
        }

        binding.rvOrders.adapter = adapter
        binding.rvOrders.layoutManager = LinearLayoutManager(this)

        viewModel.orders.observe(this, Observer { orders ->
            adapter.updateData(orders)
        })

        viewModel.fetchAllOrders()
    }

}
