package utils

import ratpack.groovy.test.GroovyRatpackMainApplicationUnderTest
import ratpack.impose.ImpositionsSpec
import ratpack.impose.UserRegistryImposition
import ratpack.registry.Registry

class ProductServiceApplicationUnderTest extends GroovyRatpackMainApplicationUnderTest {

    Registry registry

    public ProductServiceApplicationUnderTest() {
        this.getAddress()
    }

    @Override
    protected void addImpositions(ImpositionsSpec impositions) {
        impositions.add(UserRegistryImposition.of {
            registry = it
        })
    }
}
