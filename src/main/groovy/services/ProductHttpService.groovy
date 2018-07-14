package services

import com.google.inject.Inject
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

    Promise<ReceivedResponse> get(String productId) {
        httpClient.get(uri.concat("${productId}").toURI())
    }
}
