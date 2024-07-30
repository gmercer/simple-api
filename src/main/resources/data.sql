INSERT INTO users (username, password, enabled)
VALUES ('admin', '{bcrypt}$2a$10$jdJGhzsiIqYFpjJiYWMl/eKDOd8vdyQis2aynmFN0dgJ53XvpzzwC', true)
ON CONFLICT DO NOTHING;
INSERT INTO users (username, password, enabled)
VALUES ('editor', '{bcrypt}$2a$10$jdJGhzsiIqYFpjJiYWMl/eKDOd8vdyQis2aynmFN0dgJ53XvpzzwC', true)
ON CONFLICT DO NOTHING;
INSERT INTO users (username, password, enabled)
VALUES ('viewer', '{bcrypt}$2a$10$jdJGhzsiIqYFpjJiYWMl/eKDOd8vdyQis2aynmFN0dgJ53XvpzzwC', true)
ON CONFLICT DO NOTHING;
INSERT INTO users (username, password, enabled)
VALUES ('user', '{bcrypt}$2a$10$jdJGhzsiIqYFpjJiYWMl/eKDOd8vdyQis2aynmFN0dgJ53XvpzzwC', true)
ON CONFLICT DO NOTHING;
INSERT INTO groups (group_name)
VALUES ('GROUP_USERS')
ON CONFLICT DO NOTHING;
INSERT INTO groups (group_name)
VALUES ('GROUP_ADMINS')
ON CONFLICT DO NOTHING;
INSERT INTO groups (group_name)
VALUES ('GROUP_EDITORS')
ON CONFLICT DO NOTHING;
INSERT INTO groups (group_name)
VALUES ('GROUP_VIEWERS')
ON CONFLICT DO NOTHING;
INSERT INTO group_authorities (group_id, authority)
VALUES (1, 'ROLE_USER')
ON CONFLICT DO NOTHING;
INSERT INTO group_authorities (group_id, authority)
VALUES (2, 'ROLE_ADMIN')
ON CONFLICT DO NOTHING;
INSERT INTO group_authorities (group_id, authority)
VALUES (3, 'ROLE_EDITOR')
ON CONFLICT DO NOTHING;
INSERT INTO group_authorities (group_id, authority)
VALUES (4, 'ROLE_VIEWER')
ON CONFLICT DO NOTHING;
INSERT INTO group_members (username, group_id)
VALUES ('admin', 1)
ON CONFLICT DO NOTHING;
INSERT INTO group_members (username, group_id)
VALUES ('admin', 2)
ON CONFLICT DO NOTHING;
INSERT INTO group_members (username, group_id)
VALUES ('editor', 3)
ON CONFLICT DO NOTHING;
INSERT INTO group_members (username, group_id)
VALUES ('viewer', 4)
ON CONFLICT DO NOTHING;
INSERT INTO group_members (username, group_id)
VALUES ('user', 1)
ON CONFLICT DO NOTHING;

 