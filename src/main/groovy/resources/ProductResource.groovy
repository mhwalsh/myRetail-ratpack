package resources

import collections.ProductCollection
import com.google.inject.Inject
import domain.Product
import groovy.json.JsonException
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
                        render400BadRequest(context, "product id must be a number")
                    }
                }
                put {
                    String productId = pathTokens.get('id').trim()
                    if (isValidId(productId)) {
                        context.parse(Product).onError {
                            render400BadRequest(context, "body must contain valid product fields")
                        }.then { product ->
                            productCollection.updatePrice(productId, product?.price)
                            context.response.status(200)
                            context.render(json([message: "update price successfully"]))
                        }
                    } else {
                        render400BadRequest(context, "product id must be a number")
                    }
                }
            }
        }
    }

    static void render404NotFound(Context context) {
        context.response.status(404)
        context.render(json([message: "product not found"]))
    }

    static void render400BadRequest(Context context, String message) {
        context.response.status(400)
        context.render(json([message: message]))
    }

    static Boolean isValidId(String productId) {
        def regEx = /^[0-9]+$/
        productId?.matches(regEx)
    }
}
