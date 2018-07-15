package daos

import com.google.inject.Inject
import domain.Product
import services.MongoDb
import services.ProductHttpService

class ProductDao {

    @Inject
    ProductHttpService productHttpService

    @Inject
    MongoDb mongoDb

    List<Product> getProduct(String id) {
        productHttpService.getProductName(id).flatMap { httpResponse ->
            mongoDb.getProductDB()?.products?.find(productId: id)?.collect {
                new Product(productId: it.productId, name: httpResponse.getBody(), price: it.price, currencyCode: it.currencyCode)
            }
        }
    }
}
