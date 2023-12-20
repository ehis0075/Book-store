
-- Create Author table
CREATE TABLE Author (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(255) NOT NULL
);

-- Create Book table
CREATE TABLE Book (
                      id SERIAL PRIMARY KEY,
                      title VARCHAR(255) NOT NULL,
                      genre VARCHAR(20) NOT NULL,
                      isbnCode VARCHAR(13) NOT NULL,
                      author_id INT,
                      publicationYear VARCHAR(4),
                      price DECIMAL(10, 2),
                      stockCount INT NOT NULL,
                      FOREIGN KEY (author_id) REFERENCES Author(id)
);

-- Create ShoppingCart table
CREATE TABLE ShoppingCart (
                              id SERIAL PRIMARY KEY,
                              name VARCHAR(255) NOT NULL
);

-- Create Customer table
CREATE TABLE Customer (
                          id SERIAL PRIMARY KEY,
                          firstName VARCHAR(255) NOT NULL,
                          lastName VARCHAR(255) NOT NULL,
                          email VARCHAR(255) UNIQUE NOT NULL,
                          shoppingCart_id INT,
                          FOREIGN KEY (shoppingCart_id) REFERENCES ShoppingCart(id)
);


-- Create OrderLine table
CREATE TABLE OrderLine (
                           id SERIAL PRIMARY KEY,
                           book_id INT,
                           shopping_cart_id INT,
                           FOREIGN KEY (book_id) REFERENCES Book(id),
                           FOREIGN KEY (shopping_cart_id) REFERENCES ShoppingCart(id)
);

-- Insert 3 Authors
INSERT INTO Author (name) VALUES
                              ('Author 1'),
                              ('Author 2'),
                              ('Author 3');

-- Insert 3 Books with associated Authors
INSERT INTO Book (title, genre, isbnCode, author_id, publicationYear, price, stockCount) VALUES
                                                                                             ('Book 1', 'FICTION', 'ISBN001', 1, '2000', 19.99, 100),
                                                                                             ('Book 2', 'THRILLER', 'ISBN002', 2, '2005', 24.99, 75),
                                                                                             ('Book 3', 'MYSTERY', 'ISBN003', 3, '2010', 29.99, 50);

-- Insert 3 ShoppingCarts for each Customer
INSERT INTO ShoppingCart (name) VALUES
                                    ('ShoppingCart 1'),
                                    ('ShoppingCart 2'),
                                    ('ShoppingCart 3');



-- -- Insert empty OrderLine lists for each ShoppingCart
-- INSERT INTO OrderLine (book_id, shopping_cart_id) VALUES
--                                                       (1, 1),
--                                                       (2, 1),
--                                                       (3, 1);

-- Insert 3 Customers with associated ShoppingCarts
INSERT INTO Customer (firstName, lastName, email, shoppingCart_id) VALUES
                                                                       ('John', 'Doe', 'john@example.com', 1),
                                                                       ('Jane', 'Doe', 'jane@example.com', 2),
                                                                       ('Alice', 'Smith', 'alice@example.com', 3);


