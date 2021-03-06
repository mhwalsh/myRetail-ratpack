package collections

import com.gmongo.GMongo
import com.mongodb.DB
import domain.Product

/**
 * ProductCollection connection to local mongo db containing productDb with products collection
 */
class ProductCollection {
    GMongo mongo = new GMongo("127.0.0.1", 27017)

    /**
     * Returns productDb
     * @return
     */
    private DB getProductDB() {
        this.mongo.getDB('productDb')
    }

    /**
     * Method to retrieve product information from mongo via id param
     * @param id
     * @return Product domain object
     */
    Product getProduct(String id) {
        Map prodMap = getProductDB().products.findOne(productId: id)?.toMap()
        if (prodMap) {
            new Product(productId: prodMap?.productId, name: null, price: prodMap?.price, currencyCode: prodMap?.currencyCode)
        } else {
            null
        }
    }

    def updatePrice(String id, String price) {
        getProductDB().products.update([productId: id], [$set: [price: price]])
    }

    def insertProduct(Map product) {
        getProductDB().products.insert(product)
    }

    def dropProducts() {
        getProductDB().products.drop()
    }
}
