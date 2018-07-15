package resources

import collections.ProductCollection
import com.google.inject.Inject
import com.mongodb.DB
import daos.ProductDao
import ratpack.exec.Blocking
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
                    Blocking.get {
                        productDao.getProduct("53786122")
                    } then {
                        render(json(it))
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
