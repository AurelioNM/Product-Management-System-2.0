CREATE TABLE `Product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `priceBRL` decimal(17,2) NOT NULL,
  `createdAt` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
)