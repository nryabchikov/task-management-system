--liquibase formatted sql

--changeset ryabchikov:1
CREATE TABLE tasks
(
    id UUID PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(255) NOT NULL,
    status varchar(16) check (status in ('PENDING','IN_PROGRESS', 'COMPLETED')),
    priority varchar(16) check (priority in ('HIGH','MEDIUM', 'LOW')),
    author_id UUID NOT NULL,
    performer_id UUID
);

CREATE TABLE comments (
    id UUID PRIMARY KEY,
    text TEXT NOT NULL,
    author_id UUID NOT NULL,
    task_id UUID NOT NULL,
    CONSTRAINT fk_task FOREIGN KEY (task_id) REFERENCES tasks (id) ON DELETE CASCADE
);