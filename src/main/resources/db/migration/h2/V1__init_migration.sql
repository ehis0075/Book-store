CREATE TABLE author
(
    id   BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(255),
    CONSTRAINT pk_author PRIMARY KEY (id)
);

CREATE TABLE book
(
    id               BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    title            VARCHAR(255),
    genre            VARCHAR(255),
    isbn_code        VARCHAR(255),
    author_id        BIGINT,
    publication_year VARCHAR(255),
    price            DECIMAL,
    stock_count      INT,
    CONSTRAINT pk_book PRIMARY KEY (id)
);

CREATE TABLE customer
(
    id               BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    first_name       VARCHAR(255),
    last_name        VARCHAR(255),
    email            VARCHAR(255),
    shopping_cart_id BIGINT,
    CONSTRAINT pk_customer PRIMARY KEY (id)
);

CREATE TABLE order_line
(
    id      BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    book_id BIGINT,
    quantity   INT                                     NOT NULL,
    CONSTRAINT pk_orderline PRIMARY KEY (id)
);

CREATE TABLE orders
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    customer_id BIGINT,
    CONSTRAINT pk_orders PRIMARY KEY (id)
);

CREATE TABLE payment_transaction
(
    id                       BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    payment_reference_number VARCHAR(255),
    amount                   DECIMAL,
    transaction_date         TIMESTAMP,
    payment_status           VARCHAR(255),
    orders_id                BIGINT,
    customer_id              BIGINT,
    payment_channel          VARCHAR(255),
    CONSTRAINT pk_paymenttransaction PRIMARY KEY (id)
);

CREATE TABLE shopping_cart
(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    CONSTRAINT pk_shoppingcart PRIMARY KEY (id)
);

CREATE TABLE shopping_cart_order_line_list
(
    order_line_id    BIGINT NOT NULL,
    shopping_cart_id BIGINT NOT NULL,
    CONSTRAINT pk_shopping_cart_order_line_list PRIMARY KEY (order_line_id, shopping_cart_id)
);

ALTER TABLE customer
    ADD CONSTRAINT uc_customer_email UNIQUE (email);

ALTER TABLE payment_transaction
    ADD CONSTRAINT uc_paymenttransaction_paymentreferencenumber UNIQUE (payment_reference_number);

ALTER TABLE book
    ADD CONSTRAINT FK_BOOK_ON_AUTHOR FOREIGN KEY (author_id) REFERENCES author (id);

ALTER TABLE customer
    ADD CONSTRAINT FK_CUSTOMER_ON_SHOPPINGCART FOREIGN KEY (shopping_cart_id) REFERENCES shopping_cart (id);

ALTER TABLE order_line
    ADD CONSTRAINT FK_ORDERLINE_ON_BOOK FOREIGN KEY (book_id) REFERENCES book (id);

ALTER TABLE orders
    ADD CONSTRAINT FK_ORDERS_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customer (id);

ALTER TABLE payment_transaction
    ADD CONSTRAINT FK_PAYMENTTRANSACTION_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customer (id);

ALTER TABLE payment_transaction
    ADD CONSTRAINT FK_PAYMENTTRANSACTION_ON_ORDERS FOREIGN KEY (orders_id) REFERENCES orders (id);

ALTER TABLE shopping_cart_order_line_list
    ADD CONSTRAINT fk_shocarordlinlis_on_order_line FOREIGN KEY (order_line_id) REFERENCES order_line (id);

ALTER TABLE shopping_cart_order_line_list
    ADD CONSTRAINT fk_shocarordlinlis_on_shopping_cart FOREIGN KEY (shopping_cart_id) REFERENCES shopping_cart (id);