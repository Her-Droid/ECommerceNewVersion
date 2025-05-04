package id.herdroid.ecommerce.presentation.checkoutSummary

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.herdroid.ecommerce.databinding.ActivitySuccessOrderBinding
import id.herdroid.ecommerce.presentation.detailOrder.DetailOrderActivity
import id.herdroid.ecommerce.presentation.main.MainActivity

class OrderSuccessActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySuccessOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuccessOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        val orderId = intent.getStringExtra("order_id")

        binding.btnOrderDetail.setOnClickListener {
            val intent = Intent(this, DetailOrderActivity::class.java).apply {
                putExtra("order_id", orderId)
            }
            startActivity(intent)
        }

        binding.cvBackHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {

    }

}