DROP TABLE IF EXISTS offer;
 
CREATE TABLE offer (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  title VARCHAR(250) NOT NULL,
  description VARCHAR(250),
  price DOUBLE NOT NULL,
  expiredate DATE NOT NULL
);
 
INSERT INTO offer (title, description, price, expireDate) VALUES
  ('Buy one get second 1/2 price', 'Shampoo for buy 1 get second 1/2 price', 10, '2020-12-25'),
  ('Buy one get one free', 'Dove Buy one get one free', 2, '2020-12-25');