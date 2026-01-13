
DROP TABLE IF EXISTS invoice_item;
DROP TABLE IF EXISTS invoice;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS customer;

-- 1.Customer Table
CREATE TABLE customer (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          address VARCHAR(255),
                          email VARCHAR(255),
                          phone VARCHAR(20)
);

-- 2. Create Product Table
CREATE TABLE product (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         price DECIMAL(15, 2) NOT NULL,
                         gst_percentage DOUBLE,
                         stock_quantity INT
);

-- 3.Invoice Table
CREATE TABLE invoice (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         customer_id BIGINT NOT NULL,
                         invoice_date DATETIME,
                         total_amount DECIMAL(15, 2),
                         total_tax DECIMAL(15, 2),
                         discount DECIMAL(15, 2),
                         final_amount DECIMAL(15, 2),

                         CONSTRAINT fk_invoice_customer
                             FOREIGN KEY (customer_id) REFERENCES customer(id)
);

-- 4.Invoice Item Table
CREATE TABLE invoice_item (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              invoice_id BIGINT NOT NULL,
                              product_id BIGINT NOT NULL,
                              quantity INT NOT NULL,
                              price DECIMAL(15, 2),
                              tax_amount DECIMAL(15, 2),
                              total DECIMAL(15, 2),

                              CONSTRAINT fk_item_invoice
                                  FOREIGN KEY (invoice_id) REFERENCES invoice(id)
                                      ON DELETE CASCADE,

                              CONSTRAINT fk_item_product
                                  FOREIGN KEY (product_id) REFERENCES product(id)
);