package unit

import resources.ProductResource
import spock.lang.Specification

class ProductResourceSpec extends Specification {

    def 'test isValidId - #description'() {
        when:
        def result = ProductResource.isValidId(productId)

        then:
        assert result == expectedResult

        where:
        description          | productId      | expectedResult
        'valid number'       | "1234"         | true
        'not a number'       | "not a number" | false
        'special chars'      | "1234/%w)"     | false
        'leading whitespace' | " 1234"        | false
    }
}
