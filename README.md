# myRetail RESTful service

myRetail is a RESTful service that makes product data available to web and mobile clients.

## Getting Started

To run this project git clone the repository to your local machine and follow the setup sets that follow.

### Prerequisites

This project requires:
- Java & Groovy 
- Gradle
- [mongoDB](https://www.mongodb.com/)

##### Mongo
Install this database on your local machine and start it. This [guide](https://github.com/mhwalsh/lecture-guides/blob/master/mongo-install.md) walks through the mongoDB install with `brew` and `brew services`. Brew and brew services are not required to install it and get it running, but it makes things very easy to manage. If you don't have brew and word like to use it, find out more [here](https://brew.sh/).


### Installing

Run the following command inside the project directory to install project dependencies:

```
$ gradle assemble
```

To start the server run:

```
$ gradle run
```

Add some initial test data to the database by running the following command:

```
$ groovy <projectLocation>/src/test/InitDbSetUp.groovy
```
The server does not need to be up to execute this script. If database doesn't exist it will be created. If database does exist and contains data, the data will be dropped and regenerated.

### Check Step
Test the services is working locally by navigating to this this url in your preferred browser [http://localhost:5050/product/18781687](http://localhost:5050/product/18781687)

## Project structure

```
.
├── src
│   ├── main
│   │   └── groovy
│   │       ├── collections
│   │       │   └── ProductCollection.groovy
│   │       ├── domain
│   │       │   ├── CurrencyCode.groovy
│   │       │   └── Product.groovy
│   │       ├── modules
│   │       │   └── ProductModule.groovy
│   │       ├── resources
│   │       │   └── ProductResource.groovy
│   │       └── services
│   │           └── ProductHttpService.groovy
│   ├── ratpack
│   │   └── ratpack.groovy
│   └── test
│       ├── InitDbSetUp.groovy
│       └── groovy
│           ├── functional
│           │   └── ProductResourceFunctionalSpec.groovy
│           ├── unit
│           │   └── ProductResourceSpec.groovy
│           └── utils
│               └── ProductServiceApplicationUnderTest.groovy
```

## Running the tests

To run the automated tests for this service locally, run following command in the terminal:

```
$ gradle test
```

## Authors

* **Millicent Walsh** - [mhwalsh](https://github.com/mhwalsh)