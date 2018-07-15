package resources

import collections.ProductCollection
import com.google.inject.Inject
import daos.ProductDao
import domain.Product
import ratpack.groovy.handling.GroovyChainAction

import services.ProductHttpService
import static ratpack.jackson.Jackson.json

/**
 * ProductResource http entry point for the myRetail service
 */
class ProductResource extends GroovyChainAction {
    @Inject
    ProductHttpService productHttpService

    @Inject
    ProductCollection productCollection

    @Inject
    ProductDao productDao

    @Override
    void execute() throws Exception {
        // get product by product id
        path(':id?') {
            byMethod {
                get {
                    String productId = pathTokens.get('id')
                    productHttpService.getProductName(productId).then { name ->
                        Product product = productCollection.getProduct(productId)
                        product.putAt("name", name)
                        context.render(json(product))
                    }
                }
            }
        }
    }
}
