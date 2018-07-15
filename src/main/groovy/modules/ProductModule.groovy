package modules

import collections.ProductCollection
import com.google.inject.AbstractModule
import com.google.inject.Scopes
import resources.ProductResource
import services.ProductHttpService

/**
 * ProductModule guice module for simple dependency injection
 */
class ProductModule extends AbstractModule {
    protected void configure() {
        bind(ProductResource).in(Scopes.SINGLETON)
        bind(ProductHttpService).in(Scopes.SINGLETON)
        bind(ProductCollection).in(Scopes.SINGLETON)
        bind(ProductCollection).in(Scopes.SINGLETON)
    }
}
