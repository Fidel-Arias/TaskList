CREATE TABLE tasks (
    id bigint NOT NULL AUTO_INCREMENT,
    title VARCHAR(50) NOT NULL,
    description VARCHAR(255) NOT NULL,
    completed tinyint NOT NULL,
    priority VARCHAR(50) NOT NULL,
    user_id bigint NOT NULL,

    PRIMARY KEY(id),
    CONSTRAINT fk_tasks_user_id FOREIGN KEY (user_id) REFERENCES users(id)
);