import modules.ProductModule
import resources.ProductResource

import static ratpack.groovy.Groovy.ratpack

ratpack {

    bindings {
        module ProductModule
    }

    handlers {
        prefix('product'){
            all chain(registry.get(ProductResource))
        }
    }
}