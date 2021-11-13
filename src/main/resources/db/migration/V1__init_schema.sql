CREATE TABLE IF NOT EXISTS person (
	id bigint AUTO_INCREMENT PRIMARY KEY,
    username varchar(50) NOT NULL UNIQUE,
    password varchar(120) NOT NULL
);
CREATE TABLE IF NOT EXISTS messages (
	id bigint AUTO_INCREMENT PRIMARY KEY,
    text varchar(1024) NOT NULL,
    person_id bigint,
    FOREIGN KEY (person_id) REFERENCES person(id)
);