package resources

import com.google.inject.Inject
import ratpack.groovy.handling.GroovyChainAction
import services.ProductHttpService
import ratpack.jackson.Jackson

class ProductResource extends GroovyChainAction {
    @Inject
    ProductHttpService productHttpService

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

    }
}
