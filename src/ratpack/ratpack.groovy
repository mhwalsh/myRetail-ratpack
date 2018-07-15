import modules.ProductModule
import resources.ProductResource

import static ratpack.groovy.Groovy.ratpack

ratpack {

    bindings {
        module ProductModule
    }

    handlers {
        get {
            render "Hello World!"
        }
        get(":name") {
            render "Hello $pathTokens.name!"
        }

        prefix('product'){
            all chain(registry.get(ProductResource))
        }
    }
}