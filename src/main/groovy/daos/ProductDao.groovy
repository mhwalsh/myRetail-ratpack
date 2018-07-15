package daos

import com.google.inject.Inject
import services.ProductHttpService

class ProductDao {

    @Inject
    ProductHttpService productHttpService

//    Promise<Product> getProduct(String id) {
////        //call db
////        ProductCollection.find(eq("product_id", id))
////        //call redsky
////        // combine
////        //return
////    }

}
