package functional

import collections.ProductCollection
import groovy.json.JsonBuilder
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

    @Shared
    JsonSlurper slurper = new JsonSlurper()

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

    def 'test product resource get - #description'() {
        given:
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
        'not valid id'     | '1234'       | 404                | [message: "product not found"]
        'not in db'        | '18781687'   | 404                | [message: "product not found"]
    }

    def 'test product resource put - #description'() {
        given:
        createDbProduct()

        when:
        def response = client.requestSpec {
            spec ->
                spec.body.text(new JsonBuilder(requestBody).toPrettyString())
                spec.body.type("application/json;charset=UTF-8")
        }.put("product/${productId}")

        then:
        assert slurper.parseText(response.body.text) == expectedResponse

        cleanup:
        cleanUpProductDb()

        where:
        description              | requestBody      | productId  | expectedResponse
        'good request id exists' | [price: '34.56'] | '52474823' | [message: "update price successfully"]
    }
}
