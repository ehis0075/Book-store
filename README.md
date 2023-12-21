# Book Store Ecommerce Application
This is a Spring Boot application Backend API Service for a Book Store Ecommerce.

# Requirements
-Java 17 
-Maven build tool

# Getting Started
git clone https://github.com/ehis0075/Book-store.git

# Navigate to the project directory:
cd Online-Book-Store

# Build the project using Maven
mvn clean package

# Run the Application Locally
java -jar target/book-store-service-1.0.0.jar

The API service will be accessible at `http://localhost:8082/api/v1`

# Dockerize the Application
You can build a Docker image for the application using the provided Dockerfile
docker build -t book-store-service-docker:latest .

# Running the Application with Docker
Run the Docker container using the following command:

docker run -p 8080:8080 book-store-service-docker:latest

The API service will be available at http://localhost:8080/api/v1

# Continuous Integration/Continuous Deployment (CI/CD)
I have provided a jenkins file to automate the build, deployment and running of a Docker container for a service called "social-service-docker.
The pipeline has three stages: "build," "remove-old," and "run.

- The Build Stage is responsible for building a Docker image for the "social-service" using the docker build command.
- The Remove Stage removes any previously running containers named "social-service" using the docker rm -f command.
- In the Run Stage, the newly built Docker image is run as a container.


                     # book Endpoints
## search book API

**Endpoint:** `/api/v1/books/search`
**Method:** `POST`

This API Requires `title`,`genre`, `authorName`,`publicationYear`,`page`,`size` 
to be passed in the Body.
This API retrieves books based on certain criteria.



                                # Shopping cart Endpoints
## add item to Shopping cart API

**Endpoint:** `/api/v1/shoppingCarts/add`
**Method:** `POST`

 This API Requires `bookId`, and `customerId` in the request body.


## remove item from the Shopping cart API

**Endpoint:** `/api/v1/shoppingCarts/remove`
**Method:** `POST`

This API Requires `bookId`, and `customerId` in the request body.


## get all orderLine in Shopping cart API

**Endpoint:** `/api/v1/shoppingCarts/{cusomerId}`
**Method:** `POST`

This API Requires `cusomerId` as path variable.



                     # checkout Endpoints
## checkout API

**Endpoint:** `/api/v1/checkouts/checkout`
**Method:** `POST`

This API Requires `customerId`, and `amount` to be passed in the Body.
This API saves the payment transaction record with a status of pending and returns back a 
payment gateway URL. This payment gateway URL is where the customer will go to make payment.


## update orders API

**Endpoint:** `/api/v1/checkouts/update`
**Method:** `POST`

This API Requires `referenceNumber`, `paymentChannel` and `paymentStatus` to be passed in the Body.
This API update payment transaction record with either successful or failed status after
payment has been made by the customer.


                    # Payment transaction Endpoints
## get all Payment transaction API

**Endpoint:** `/api/v1/transactions/{customerId}`
**Method:** `GET`

This API Requires `customerId` to be passsed as a path variable and  `pageSize` and `pageNumber` to be passed as request param.
This API update payment transaction record with either successful or failed status after
payment has been made by the customer.
