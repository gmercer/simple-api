INSERT INTO users (username, password, enabled)
VALUES ('admin', '{bcrypt}$2a$10$jdJGhzsiIqYFpjJiYWMl/eKDOd8vdyQis2aynmFN0dgJ53XvpzzwC', true);
INSERT INTO groups (group_name)
VALUES ('GROUP_USERS');
INSERT INTO groups (group_name)
VALUES ('GROUP_ADMINS');
INSERT INTO group_authorities (group_id, authority)
VALUES (1, 'ROLE_USER');
INSERT INTO group_authorities (group_id, authority)
VALUES (2, 'ROLE_ADMIN');
INSERT INTO group_members (username, group_id)
VALUES ('admin', 1);
INSERT INTO group_members (username, group_id)
VALUES ('admin', 2);
