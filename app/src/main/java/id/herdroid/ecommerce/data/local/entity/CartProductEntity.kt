package id.herdroid.ecommerce.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "cart_products")
@Parcelize
data class CartProductEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val price: Double,
    val image: String,
    var quantity: Int = 1
) : Parcelable
