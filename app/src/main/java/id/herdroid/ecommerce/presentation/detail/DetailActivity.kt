package id.herdroid.ecommerce.presentation.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import id.herdroid.ecommerce.R
import id.herdroid.ecommerce.databinding.ActivityDetailBinding
import id.herdroid.ecommerce.domain.model.Product
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()
    private var isFavorite = false
    private lateinit var currentProduct: Product
    private var menu: Menu? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.topAppBar)
        binding.topAppBar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        val title = intent.getStringExtra("title") ?: ""
        val image = intent.getStringExtra("image") ?: ""
        val price = intent.getDoubleExtra("price", 0.0)
        val desc = intent.getStringExtra("description") ?: ""
        val category = intent.getStringExtra("category") ?: ""
        val productId = intent.getIntExtra("product_id", -1)


        currentProduct = Product(
            id = productId,
            title = title,
            price = price,
            description = desc,
            category = category,
            image = image
        )


        binding.tvTitle.text = title
        binding.tvPrice.text = "$${price}"
        binding.tvCategory.text = category
        binding.tvDescription.text = desc
        Glide.with(this).load(image).into(binding.ivProduct)

        binding.btnAddToCart.setOnClickListener {
            if (productId != -1) {
                viewModel.addToCart(productId)
                Toast.makeText(this, "Added to cart!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        this.menu = menu
        checkIfFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_favorite -> {
                toggleFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun toggleFavorite() {
        isFavorite = !isFavorite
        if (isFavorite) {
            viewModel.addToFavorites(currentProduct)
            Toast.makeText(this, "Added to Favorites", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.removeFromFavorites(currentProduct.id)
            Toast.makeText(this, "Removed from Favorites", Toast.LENGTH_SHORT).show()
        }
        updateFavoriteIcon()
    }


    private fun updateFavoriteIcon() {
        val iconRes = if (isFavorite) {
            R.drawable.ic_favorite_save
        } else {
            R.drawable.ic_favorite_nov_save
        }
        menu?.findItem(R.id.action_favorite)?.setIcon(iconRes)
    }

    private fun checkIfFavorite() {
        lifecycleScope.launch {
            viewModel.isFavorite(currentProduct.id).collect { result ->
                isFavorite = result
                updateFavoriteIcon()
            }
        }
    }

}

