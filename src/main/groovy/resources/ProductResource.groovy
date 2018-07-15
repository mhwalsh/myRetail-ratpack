package resources

import collections.ProductCollection
import com.google.inject.Inject
import daos.ProductDao
import domain.Product
import groovy.json.JsonSlurper
import ratpack.groovy.handling.GroovyChainAction
import services.MongoDb
import services.ProductHttpService
import static ratpack.jackson.Jackson.json


class ProductResource extends GroovyChainAction {
    @Inject
    ProductHttpService productHttpService

    @Inject
    ProductCollection productCollection

    @Inject
    ProductDao productDao

    @Inject
    MongoDb mongoDb

    @Override
    void execute() throws Exception {
        path(':id?') {
            byMethod {
                get {
                    String productId = pathTokens.get('id')
                    productHttpService.getProductName(productId).then { name ->
                        List<Product> products = mongoDb.getProduct(productId)
                        Product product = products.get(0)
                        product.putAt("name", name)
                        context.render(json(product))
                    }
                }
            }
        }
    }
}
