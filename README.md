# Password-Manager
Java based password manager. Uses java, javafx, fxml, css, and uses local MySQL database

Need to have MySQL installed, along with the java connector. Connector must be included in the build path.
Create the schema and tables yourself and create a user named password_manager in MySQL that can only 
access password_manager schema. This can be done in the MySQL Workbench

Executable jar is in folder. Download jar and batch file. To run, either execute batch command in command prompt under the same directory of jar file or double click batch file (assuming jar and batch file are in the same directory).

Table Format:
create schema password_manager;
use password_manager;
CREATE TABLE `sites` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `user_name` varchar(32) NOT NULL,
  `username` varchar(32) NOT NULL,
  `domain_name` varchar(32) NOT NULL,
  `url` varchar(32) NOT NULL,
  `cypher_text` blob,
  `secret_key` blob,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`,`user_name`),
  CONSTRAINT `sites_ibfk_1` FOREIGN KEY (`user_id`, `user_name`) REFERENCES `users` (`id`, `user_name`)
); 

CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(32) NOT NULL,
  `first_name` varchar(32) NOT NULL,
  `last_name` varchar(32) NOT NULL,
  `salt` blob,
  `encrypted_password` blob,
  PRIMARY KEY (`id`,`user_name`)
);


