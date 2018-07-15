package functional

import groovy.json.JsonSlurper
import ratpack.groovy.test.GroovyRatpackMainApplicationUnderTest
import ratpack.test.ApplicationUnderTest
import ratpack.test.MainClassApplicationUnderTest
import ratpack.test.http.TestHttpClient
import spock.lang.Shared
import spock.lang.Specification


class ProductResourceFunctionalSpec extends Specification {

    def 'test product resource - #description'() {
        given:
        def aut = new GroovyRatpackMainApplicationUnderTest()
        JsonSlurper slurper = new JsonSlurper()

        when:
        def response = aut.httpClient.get('product/52474823')

        then:
        assert response.statusCode == expectedStatusCode
        assert slurper.parseText(response.body.text) == expectedResponse

        where:
        description  | expectedStatusCode | expectedResponse
        'happy path' | 200                | [productId: "52474823", name: "Men's Jefferson Loafer Dress Shoe - Goodfellow & Co&#153; Black", price: "34.99", currencyCode: "USD"]
    }
}
