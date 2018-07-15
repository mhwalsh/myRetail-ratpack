package resources

import collections.ProductCollection
import com.google.inject.Inject
import com.mongodb.DB
import daos.ProductDao
import domain.Product
import groovy.json.JsonSlurper
import ratpack.exec.Blocking
import ratpack.exec.Promise
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
        path('all') {
            byMethod {
                get {
//                    flatMapError
                    productHttpService.get("53786122").then { httpResponse ->
                        List<Product> products = mongoDb.getProduct("53786122")
                        Product product = products.get(0)
                        def jsonSlurper = new JsonSlurper()
                        product.putAt("name", jsonSlurper.parseText(httpResponse.getBody().getText()).product.item.product_description.title)
                        context.render(json(product))
                    }
                }
            }
        }
        path(':id?') {
            byMethod {
                get {
                    productHttpService.get(pathTokens.get('id')).then { response ->
                        context.render(response.getBody().getText())
                    }
                }
            }
        }
    }

}
