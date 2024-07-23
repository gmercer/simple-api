INSERT INTO users (username, password, enabled)
VALUES ('admin', '{bcrypt}$2a$10$jdJGhzsiIqYFpjJiYWMl/eKDOd8vdyQis2aynmFN0dgJ53XvpzzwC', true) ON CONFLICT DO NOTHING ;
INSERT INTO groups (group_name)
VALUES ('GROUP_USERS') ON CONFLICT DO NOTHING ;
INSERT INTO groups (group_name)
VALUES ('GROUP_ADMINS') ON CONFLICT DO NOTHING ;
INSERT INTO group_authorities (group_id, authority)
VALUES (1, 'ROLE_USER') ON CONFLICT DO NOTHING ;
INSERT INTO group_authorities (group_id, authority)
VALUES (2, 'ROLE_ADMIN') ON CONFLICT DO NOTHING ;
INSERT INTO group_members (username, group_id)
VALUES ('admin', 1) ON CONFLICT DO NOTHING ;
INSERT INTO group_members (username, group_id)
VALUES ('admin', 2) ON CONFLICT DO NOTHING;
