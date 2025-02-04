CREATE TABLE IF NOT EXISTS material_types
(
    id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    material_type varchar(255),
    percentage_of_defect varchar(5)
);

CREATE TABLE IF NOT EXISTS partner_products
(
    id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    product varchar(255),
    partner_name varchar(255),
    quantity INTEGER,
    sale_date DATE
);

CREATE TABLE IF NOT EXISTS partners
(
    id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    type_partner varchar(8),
    name_partner varchar(40),
    director varchar(200),
    email varchar(100),
    phone varchar(13),
    address varchar(500),
    inn varchar(10),
    rating INTEGER
);

CREATE TABLE IF NOT EXISTS product_types
(
    id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    product_type varchar(255),
    coefficient money
);

CREATE TABLE IF NOT EXISTS products
(
    id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    product_type varchar(255),
    name varchar(255),
    article INTEGER,
    minimal_price money
);
