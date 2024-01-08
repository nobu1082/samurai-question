 CREATE TABLE IF NOT EXISTS reviews (
 	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
 	name VARCHAR(255) NOT NULL,
 	commenttext VARCHAR(255) NOT NULL,
 	value INT NOT NULL,
 	updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
 );