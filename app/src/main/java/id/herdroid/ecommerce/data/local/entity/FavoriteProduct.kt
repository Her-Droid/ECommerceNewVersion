package id.herdroid.ecommerce.data.local.entity

import id.herdroid.ecommerce.domain.model.Product
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteProduct(
    @PrimaryKey val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String
) {
    companion object {
        fun fromProduct(product: Product): FavoriteProduct {
            return FavoriteProduct(
                id = product.id,
                title = product.title,
                price = product.price,
                description = product.description,
                category = product.category,
                image = product.image
            )
        }

        fun toProduct(fav: FavoriteProduct): Product {
            return Product(
                id = fav.id,
                title = fav.title,
                price = fav.price,
                description = fav.description,
                category = fav.category,
                image = fav.image
            )
        }
    }
}
