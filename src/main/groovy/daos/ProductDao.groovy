package daos

import com.google.inject.Inject
import domain.Product
import ratpack.exec.Promise
import services.MongoDb
import services.ProductHttpService

class ProductDao {

    @Inject
    ProductHttpService productHttpService

    @Inject
    MongoDb mongoDb

    List<Product> getProduct(String id) {
        //call db
        mongoDb.getProductDB()?.products?.find(productId: id)?.collect {
            new Product(productId: it.productId, name: null, price: it.price, currencyCode: it.currencyCode)
        }

        //call redsky
        // combine
        //return
    }

}
