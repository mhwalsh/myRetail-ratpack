package services

import com.gmongo.GMongo
import com.mongodb.DB
import domain.Product
import ratpack.exec.Promise

class MongoDb {
    GMongo mongo = new GMongo("127.0.0.1", 27017)

    DB getProductDB() {
        this.mongo.getDB('productDb')
    }

    List<Product> getProduct(String id) {
        getProductDB().products.find(productId: id).toList().collect {
            new Product(productId: it.productId, name: null, price: it.price, currencyCode: it.currencyCode)
        }
    }
}
