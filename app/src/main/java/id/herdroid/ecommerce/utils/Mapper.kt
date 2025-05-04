package id.herdroid.ecommerce.utils

import id.herdroid.ecommerce.data.remote.dto.ProductDto
import id.herdroid.ecommerce.domain.model.Product


fun ProductDto.toProduct(): Product {
    return Product(
        id = id,
        title = title,
        price = price,
        description = description,
        category = category,
        image = image
    )
}





