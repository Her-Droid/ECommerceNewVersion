package id.herdroid.ecommerce.presentation.cart

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.herdroid.ecommerce.R
import id.herdroid.ecommerce.data.local.entity.CartProductEntity
import id.herdroid.ecommerce.databinding.ActivityCartBinding
import id.herdroid.ecommerce.presentation.checkoutSummary.CheckoutSummaryActivity

@AndroidEntryPoint
class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private val viewModel: CartViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.topAppBar)
        binding.topAppBar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        val adapter = CartAdapter(
            onDelete = { viewModel.deleteProduct(it.id) },
            onQuantityChange = { viewModel.updateQuantity(it) }
        )

        binding.rvCart.adapter = adapter
        binding.rvCart.layoutManager = LinearLayoutManager(this)

        viewModel.cartProducts.observe(this) { products ->
            adapter.submitList(products)
            calculateTotal(products)
            checkIfCartIsEmpty(products)
        }

        binding.btnDeleteAll.setOnClickListener {
            viewModel.clearCart()
        }

        binding.btnCheckout.setOnClickListener {
            val totalPriceString = binding.tvTotalPrice.text.toString().removePrefix("Total Shopping: $").replace(",", "")
            val totalPrice = totalPriceString.toDoubleOrNull() ?: 0.0
            val intent = Intent(this, CheckoutSummaryActivity::class.java).apply {
                putParcelableArrayListExtra("cartProducts", ArrayList(viewModel.cartProducts.value))
                putExtra("totalPrice", totalPrice)
            }
            startActivity(intent)
        }
    }

    private fun calculateTotal(products: List<CartProductEntity>) {
        val total = products.sumOf { it.price * it.quantity }
        binding.tvTotalPrice.text = "Total Shopping: $${String.format("%.2f", total)}"
    }

    private fun checkIfCartIsEmpty(products: List<CartProductEntity>) {
        val isCartEmpty = products.isEmpty() || products.sumOf { it.price * it.quantity } == 0.0
        binding.btnCheckout.isEnabled = !isCartEmpty

        val drawableRes = if (isCartEmpty) {
            R.drawable.rounded_button_off_bg
        } else {
            R.drawable.rounded_button_bg
        }
        binding.btnCheckout.background = ContextCompat.getDrawable(this, drawableRes)
    }

}



