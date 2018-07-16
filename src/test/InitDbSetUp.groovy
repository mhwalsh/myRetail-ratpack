@Grapes([
        @Grab(group='com.gmongo', module='gmongo', version='1.0')
])

import com.gmongo.GMongo
def mongo = new GMongo("127.0.0.1", 27017)

// Get a db reference in the old fashion way
def db = mongo.getDB("productDb")

//drop db
db.products.drop()

// add a few documents
db.products << [[productId: "53786122", price: "7.99", currencyCode: "USD"]]
db.products << [[productId: "52474823", price: "34.99", currencyCode: "USD"]]
db.products << [[productId: "18781687", price: "249.99", currencyCode: "USD"]]