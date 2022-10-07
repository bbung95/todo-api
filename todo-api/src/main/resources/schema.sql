DROP TABLE IF EXISTS `task`;
DROP TABLE IF EXISTS `board`;
DROP TABLE IF EXISTS `user`;

CREATE TABLE `task` (
                        `id`	INT	NOT NULL AUTO_INCREMENT PRIMARY KEY,
                        `board_id`	INT	NOT NULL,
                        `title`	VARCHAR(100)	NULL,
                        `contents`	VARCHAR(255)	NULL,
                        `status`	VARCHAR(20)	NULL,
                        `orders`	INT	NULL,
                        `importance`	VARCHAR(20)	NULL,
                        `create_date`	DATETIME	NULL,
                        `modified_date`	DATETIME	NULL
);

CREATE TABLE `board` (
                         `id`	INT	NOT NULL AUTO_INCREMENT PRIMARY KEY,
                         `writer`	INT	NOT NULL,
                         `title`	VARCHAR(100)	NULL,
                         `create_date`	DATETIME	NULL,
                         `modify_date`	DATETIME	NULL
);

CREATE TABLE `users` (
                         `id`	INT	NOT NULL AUTO_INCREMENT PRIMARY KEY,
                         `username`	VARCHAR(30)	NULL,
                         `password`	VARCHAR(255)	NULL,
                         `nickname`	VARCHAR(100)	NULL,
                         `create_date`	DATETIME	NULL,
                         `role`	VARCHAR(20)	NULL
);
