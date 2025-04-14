CREATE TABLE users (
    id bigint NOT NULL AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,

    PRIMARY KEY(id)
);