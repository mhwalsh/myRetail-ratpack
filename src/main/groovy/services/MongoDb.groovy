package services

import com.gmongo.GMongo
import com.mongodb.DB

class MongoDb {
    GMongo mongo = new GMongo("127.0.0.1", 27017)

    DB getProductDB() {
        this.mongo.getDB('productDb')
    }
}
