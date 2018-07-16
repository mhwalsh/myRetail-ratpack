package services

import com.google.inject.Inject
import groovy.json.JsonSlurper
import ratpack.exec.Promise
import ratpack.http.client.HttpClient

/**
 * ProductHttpService service to call redsky service to retrieve product data
 */
class ProductHttpService {
    private HttpClient httpClient
    private String uri = "https://redsky.target.com/v2/pdp/tcin/"

    @Inject
    ProductHttpService(HttpClient httpClient) {
        this.httpClient = httpClient
    }

    /**
     * Get a product name from redsky by passing a product id
     * @param productId
     * @return
     */
    Promise<Object> getProductName(String productId) {
        JsonSlurper jsonSlurper = new JsonSlurper()

        httpClient.get(uri.concat("${productId}").toURI()).flatMap { httpResponse ->
            def name = jsonSlurper.parseText(httpResponse.body.text)?.product?.item?.product_description?.title
            if (name) {
                Promise.value(name)
            } else {
                throw new RuntimeException("no redsky item found")
            }
        }
    }
}
