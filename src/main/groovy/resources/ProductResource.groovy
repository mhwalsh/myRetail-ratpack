package resources

import collections.ProductCollection
import com.google.inject.Inject
import daos.ProductDao
import domain.Product
import groovy.json.JsonSlurper
import ratpack.groovy.handling.GroovyChainAction
import ratpack.handling.Context
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
                    String productId = pathTokens.get('id').trim()
                    if (isValidId(productId)) {
                        productHttpService.getProductName(productId).flatMapError {
                            render404NotFound(context)
                        }.then { name ->
                            Product product = productCollection.getProduct(productId)
                            product?.putAt("name", name)
                            if (product) {
                                context.render(json(product))
                            } else {
                                render404NotFound(context)
                            }
                        }
                    } else {
                        context.response.status(400)
                        context.render(json([message: "product id must be a number"]))
                    }
                }
            }
        }
    }

    static void render404NotFound(Context context) {
        context.response.status(404)
        context.render(json([message: "product not found"]))
    }

    static Boolean isValidId(String productId) {
        def regEx = /^[0-9]+$/
        productId?.matches(regEx)
    }
}
