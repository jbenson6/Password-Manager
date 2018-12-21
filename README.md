# Password-Manager
Java based password manager. Uses java, javafx, fxml, css, and uses local MySQL database

Need to have MySQL installed, along with the java connector. Connector must be included in the build path.
Create the schema and tables yourself and create a user named password_manager in MySQL that can only 
access password_manager schema. This can be done in the MySQL Workbench

Table Format:
CREATE TABLE `sites` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `user_name` varchar(32) NOT NULL,
  `username` varchar(32) NOT NULL,
  `domain_name` varchar(32) NOT NULL,
  `url` varchar(32) NOT NULL,
  `cypher_text` blob,
  `secret_key` blob,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`,`user_name`),
  CONSTRAINT `sites_ibfk_1` FOREIGN KEY (`user_id`, `user_name`) REFERENCES `users` (`id`, `user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(32) NOT NULL,
  `first_name` varchar(32) NOT NULL,
  `last_name` varchar(32) NOT NULL,
  `salt` blob,
  `encrypted_password` blob,
  PRIMARY KEY (`id`,`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


