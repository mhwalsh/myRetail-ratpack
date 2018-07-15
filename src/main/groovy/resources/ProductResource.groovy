package resources

import collections.ProductCollection
import com.google.inject.Inject
import ratpack.groovy.handling.GroovyChainAction
import services.ProductHttpService

class ProductResource extends GroovyChainAction {
    @Inject
    ProductHttpService productHttpService

    @Inject
    ProductCollection productCollection

    @Override
    void execute() throws Exception {
        path('/:id?') {
            byMethod {
                get {
                    productHttpService.get(pathTokens.get('id')).then { response ->
                        context.render(response.getBody().getText())
                    }
                }
            }
        }
        path('/all') {
            byMethod {
                get {
                    render 'nothing'
                }
            }
        }
    }
}
