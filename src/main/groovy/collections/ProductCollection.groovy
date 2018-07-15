package collections

import com.gmongo.GMongo
import com.mongodb.DB
import domain.Product

class ProductCollection {
    GMongo mongo = new GMongo("127.0.0.1", 27017)

    private DB getProductDB() {
        this.mongo.getDB('productDb')
    }

    Product getProduct(String id) {
        Map prodMap = getProductDB().products.findOne(productId: id).toMap()
        new Product(productId: prodMap.productId, name: null, price: prodMap.price, currencyCode: prodMap.currencyCode)
    }
}
