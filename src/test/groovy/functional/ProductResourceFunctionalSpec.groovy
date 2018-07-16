package functional

import collections.ProductCollection
import groovy.json.JsonSlurper
import ratpack.registry.Registry
import ratpack.test.http.TestHttpClient
import spock.lang.Shared
import spock.lang.Specification
import utils.ProductServiceApplicationUnderTest

class ProductResourceFunctionalSpec extends Specification {

    @Shared
    ProductCollection productCollection

    @Shared
    TestHttpClient client

    @Shared
    Registry registry

    @Shared
    def aut = new ProductServiceApplicationUnderTest()

    def setupSpec() {
        client = aut.httpClient
        registry = aut.registry
        productCollection = registry.get(ProductCollection)
    }

    def createDbProduct() {
        productCollection.insertProduct([productId: "52474823", price: "34.99", currencyCode: "USD"])
    }

    def cleanUpProductDb() {
        productCollection.dropProducts()
    }

    def 'test product resource - #description'() {
        given:
        JsonSlurper slurper = new JsonSlurper()
        createDbProduct()

        when:
        def response = client.get("product/${productId}")

        then:
        assert response.statusCode == expectedStatusCode
        assert slurper.parseText(response.body.text) == expectedResponse

        cleanup:
        cleanUpProductDb()

        where:
        description        | productId    | expectedStatusCode | expectedResponse
        'valid product id' | '52474823'   | 200                | [productId: "52474823", name: "Men's Jefferson Loafer Dress Shoe - Goodfellow & Co&#153; Black", price: "34.99", currencyCode: "USD"]
        'id non numeric'   | 'notanumber' | 400                | [message: "product id must be a number"]
        // id not in db
    }
}
