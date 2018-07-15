package services

import com.google.inject.Inject
import groovy.json.JsonSlurper
import ratpack.exec.Promise
import ratpack.http.client.HttpClient
import ratpack.http.client.ReceivedResponse

class ProductHttpService {
    private HttpClient httpClient
    private String uri = "https://redsky.target.com/v2/pdp/tcin/"

    @Inject
    ProductHttpService(HttpClient httpClient) {
        this.httpClient = httpClient
    }

    Promise<Object> getProductName(String productId) {
        httpClient.get(uri.concat("${productId}").toURI()).flatMap { httpResponse ->
            JsonSlurper jsonSlurper = new JsonSlurper()
            Promise.value(jsonSlurper.parseText(httpResponse.getBody().getText()).product.item.product_description.title)
        }
    }
}
