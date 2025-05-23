package id.herdroid.ecommerce.presentation.checkoutSummary

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import id.herdroid.ecommerce.R
import id.herdroid.ecommerce.data.local.entity.CartProductEntity
import id.herdroid.ecommerce.data.local.entity.OrderDetailsEntity
import id.herdroid.ecommerce.data.local.entity.OrderEntity
import id.herdroid.ecommerce.databinding.ActivityCheckoutSummaryBinding
import id.herdroid.ecommerce.presentation.listOder.ListOrderActivity
import id.herdroid.ecommerce.utils.AesEncryptor
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.UUID

@AndroidEntryPoint
class CheckoutSummaryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckoutSummaryBinding
    private var cartProducts: List<CartProductEntity> = emptyList()
    private var totalPrice: Double = 0.0
    private val viewModel: CheckoutSummaryViewModel by viewModels()
    private var selectedOrderDateMillis: Long = System.currentTimeMillis()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutSummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.topAppBar)
        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        cartProducts = intent.getParcelableArrayListExtra<CartProductEntity>("cartProducts") ?: emptyList()
        totalPrice = intent.getDoubleExtra("totalPrice", 0.0)

        binding.tvDeliveryMethod.text = "Standard Delivery (2-3 days)"
        binding.tvPaymentMethod.text = "Cash on Delivery (COD)"

        updateProductList(cartProducts)
        calculateSummary(cartProducts)

        binding.btnPlaceOrder.setOnClickListener {
            placeOrder()
        }

        setupOrderDatePicker()


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupOrderDatePicker() {
        val today = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy", Locale.ENGLISH)
        binding.etOrderDate.setText(today.format(formatter))

        val constraintsBuilder = CalendarConstraints.Builder()
            .setValidator(DateValidatorPointForward.now())

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select Order Date")
            .setCalendarConstraints(constraintsBuilder.build())
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        binding.etOrderDate.setOnClickListener {
            datePicker.show(supportFragmentManager, "ORDER_DATE_PICKER")
        }
        datePicker.addOnPositiveButtonClickListener { selection ->
            selectedOrderDateMillis = selection
            val sdf = SimpleDateFormat("EEEE, d MMMM yyyy", Locale.ENGLISH)
            val date = Date(selection)
            binding.etOrderDate.setText(sdf.format(date))
        }


        binding.orderDateLayout.setEndIconDrawable(R.drawable.ic_calendar)
        binding.orderDateLayout.setEndIconOnClickListener {
            datePicker.show(supportFragmentManager, "ORDER_DATE_PICKER")
        }
    }


    private fun updateProductList(products: List<CartProductEntity>) {
        val adapter = CheckoutProductAdapter()
        binding.rvProducts.adapter = adapter
        binding.rvProducts.layoutManager = LinearLayoutManager(this)
        adapter.submitList(products)
    }

    private fun calculateSummary(products: List<CartProductEntity>) {
        val productSubtotal = products.sumOf { it.price * it.quantity }
        val shippingCost = 5.00
        val serviceFee = 2.00
        val total = productSubtotal + shippingCost + serviceFee

        binding.tvProductSubtotal.text = "$${String.format("%.2f", productSubtotal)}"
        binding.tvShippingSubtotal.text = "$${String.format("%.2f", shippingCost)}"
        binding.tvServiceFee.text = "$${String.format("%.2f", serviceFee)}"
        binding.tvTotalAmount.text ="Total : " + "$${String.format("%.2f", total)}"
    }

    private fun placeOrder() {
        val address = binding.etShippingAddress.text.toString().trim()
        val productItemsJson = Gson().toJson(cartProducts)
        if (address.isEmpty()) {
            Toast.makeText(this, "Please enter delivery address", Toast.LENGTH_SHORT).show()
            return
        }

        val productSubtotal = cartProducts.sumOf { it.price * it.quantity }
        val shippingCost = 5.00
        val serviceFee = 2.00
        val total = productSubtotal + shippingCost + serviceFee

        val rawOrderId = AesEncryptor.generateShortOrderId()
        val encryptedOrderId = AesEncryptor.encrypt(rawOrderId)

        val order = OrderEntity(
            orderId = encryptedOrderId,
            orderDate = selectedOrderDateMillis,
            totalPrice = total,
            itemCount = cartProducts.sumOf { it.quantity },
            address = address,
            paymentMethod = "Cash on Delivery (COD)",
            productItems = productItemsJson
        )

        val orderDetailsEntity = OrderDetailsEntity(
            orderId = encryptedOrderId,
            orderDate = selectedOrderDateMillis,
            totalPrice = total,
            productSubtotal = productSubtotal,
            deliveryFee = shippingCost,
            serviceFee = serviceFee,
            address = address,
            paymentMethod = "Cash on Delivery",
            deliveryMethod = "Standard Delivery (2-3 days)",
            productItems = productItemsJson
        )

        viewModel.insertOrder(order, orderDetailsEntity)

        val intent = Intent(this, OrderSuccessActivity::class.java).apply {
            putExtra("order_id", encryptedOrderId)
        }
        startActivity(intent)
        finish()
    }



}
