package services

import com.google.inject.Inject
import ratpack.exec.Promise
import ratpack.http.client.HttpClient
import ratpack.http.client.ReceivedResponse

class ProductHttpService {
    private HttpClient httpClient
    private String uri = "https://redsky.target.com/v2/pdp/tcin/13860428?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics"

    @Inject
    ProductHttpService(HttpClient httpClient) {
        this.httpClient = httpClient
    }

    Promise<ReceivedResponse> get() {
        httpClient.get(uri.toURI())
    }
}
