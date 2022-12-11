CREATE DATABASE order_service;

Use order_service;

CREATE TABLE products(
                         id int primary key auto_increment,
                         description varchar(128),
                         price double
);

CREATE TABLE orders(
                       id int primary key auto_increment,
                       Date_Time datetime,
                       finished boolean
);

CREATE TABLE items(
                      id int primary key auto_increment,
                      product_id int,
                      price double,
                      count int,
                      order_id int not null,
                      constraint fk_orders foreign key (order_id) references orders(id)
);

SELECT * from orders o left join items i on o.id = i.order_id where o.id = 7
