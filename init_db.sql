-- 创建数据库
CREATE DATABASE IF NOT EXISTS jpa_demo CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE jpa_demo;

-- 创建表和外键约束
-- 1. 用户表
CREATE TABLE t_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL
);

-- 2. 用户详情表 (1-1 关系)
-- user_id 是外键，且必须加上 UNIQUE 约束，才能保证物理层面是一对一
CREATE TABLE t_detail (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    address VARCHAR(255),
    user_id BIGINT UNIQUE,
    CONSTRAINT fk_detail_user FOREIGN KEY (user_id) REFERENCES t_user(id)
);

-- 3. 文章表 (1-n 关系)
-- user_id 是外键，一个用户对应多行数据
CREATE TABLE t_post (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100),
    user_id BIGINT,
    CONSTRAINT fk_post_user FOREIGN KEY (user_id) REFERENCES t_user(id)
);

-- 4. 标签表
CREATE TABLE t_tag (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50)
);

-- 5. 文章-标签中间表 (n-m 关系)
-- 需要两个外键，分别指向 Post 和 Tag
CREATE TABLE t_post_tag (
    post_id BIGINT,
    tag_id BIGINT,
    PRIMARY KEY (post_id, tag_id),
    CONSTRAINT fk_pt_post FOREIGN KEY (post_id) REFERENCES t_post(id),
    CONSTRAINT fk_pt_tag FOREIGN KEY (tag_id) REFERENCES t_tag(id)
);