

DROP SCHEMA IF EXISTS `RETAILDEV`;

CREATE SCHEMA `RETAILDEV`;

use `RETAILDEV`;

SET FOREIGN_KEY_CHECKS = 0;


CREATE TABLE `PRODUCT_MASTER` (
  `id`  BIGINT(19) NOT NULL AUTO_INCREMENT,
  `barCodeId` varchar(45) DEFAULT NULL	,
  `name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `productCategory` char(1) DEFAULT NULL,
  `rate` float(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

insert into PRODUCT_MASTER  (barCodeId,name,email,productCategory,rate)  values  ('XX123','Lux','LuxStoxk@gmail.com','A',120);
insert into PRODUCT_MASTER  (barCodeId,name,email,productCategory,rate)  values  ('XX124','Pears','PearsStock@gmail.com','B',20);
insert into PRODUCT_MASTER  (barCodeId,name,email,productCategory,rate)  values  ('XX125','Maggi','MaggiStock@gmail.com','A',420);
insert into PRODUCT_MASTER  (barCodeId,name,email,productCategory,rate)  values  ('XX126','Oreo','OreoStock@gmail.com','C',300);
insert into PRODUCT_MASTER  (barCodeId,name,email,productCategory,rate)  values  ('XX127','Ceralac','CeralacStock@gmail.com','A',200);


CREATE TABLE `bills` (
  `bill_id` bigint(19) NOT NULL AUTO_INCREMENT,
  `noOfItems` int(10) DEFAULT NULL,
  `totalCost` float DEFAULT NULL,
  `totalTax` float DEFAULT NULL,
  `totalValue` float DEFAULT NULL,
  `billStatus` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`bill_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;

CREATE TABLE `cart_item` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `quantity` bigint(10) DEFAULT NULL,
  `product_id` bigint(19) DEFAULT NULL,
  `bill_id` bigint(19) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_PRODUCT` (`product_id`),
  KEY `FK_BILL` (`bill_id`),
  CONSTRAINT `FK_BILL` FOREIGN KEY (`bill_id`) REFERENCES `bills` (`bill_id`),
  CONSTRAINT `FK_PRODUCT` FOREIGN KEY (`product_id`) REFERENCES `product_master` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;


