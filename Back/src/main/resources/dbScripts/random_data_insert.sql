-- Вставка значений в таблицу "users"
-- Вставка данных в таблицу users
INSERT INTO users (username, email, usr_password, preferences)
VALUES
    ('john_doe', 'john@example.com', 'password123', '{"theme": "dark", "notifications": true}'),
    ('jane_smith', 'jane@example.com', 'password456', '{"theme": "light", "notifications": false}'),
    ('mike_jackson', 'mike@example.com', 'password789', '{"theme": "dark", "notifications": true}'),
    ('sara_williams', 'sara@example.com', 'password101', '{"theme": "light", "notifications": true}');

-- Вставка данных в таблицу boards
INSERT INTO boards (title, last_edit)
VALUES
    ('Project A', '2023-10-01 09:30:00'),
    ('Project B', '2023-09-25 14:15:00'),
    ('Tasks', '2023-10-05 11:20:00'),
    ('Ideas', '2023-10-02 10:00:00');

-- Вставка данных в таблицу lists
INSERT INTO lists (title, list_position, board_id)
VALUES
    ('To Do', 1, 1),
    ('In Progress', 2, 1),
    ('Done', 3, 1),
    ('Backlog', 4, 1);

-- Вставка данных в таблицу tasks
INSERT INTO tasks (task_text, task_position, list_id, due_date, task_doer_id, task_preferences)
VALUES
    ('Task 1', 1, 1, '2023-10-10', 1, '{"priority": "high"}'),
    ('Task 2', 2, 1, '2023-10-12', 2, '{"priority": "medium"}'),
    ('Task 3', 1, 2, '2023-10-15', 3, '{"priority": "low"}'),
    ('Task 4', 3, 3, '2023-10-18', 4, '{"priority": "high"}');

-- Вставка данных в таблицу labels
INSERT INTO labels (title, label_info)
VALUES
    ('Label 1', 'Information about Label 1'),
    ('Label 2', 'Information about Label 2'),
    ('Label 3', 'Information about Label 3'),
    ('Label 4', 'Information about Label 4');

-- Вставка данных в таблицу comments
INSERT INTO comments (task_id, user_id, comment_text, comment_date)
VALUES
    (1, 1, 'This is a comment for Task 1', '2023-10-10 11:30:00'),
    (2, 2, 'This is a comment for Task 2', '2023-10-12 15:45:00'),
    (3, 3, 'This is a comment for Task 3', '2023-10-15 09:00:00'),
    (4, 4, 'This is a comment for Task 4', '2023-10-18 10:30:00');

-- Вставка данных в таблицу roles
INSERT INTO roles (board_id, role_name, role_permissions)
VALUES
    (1, 'Admin', '{"edit": true, "delete": true}'),
    (2, 'Member', '{"edit": false, "delete": false}'),
    (3, 'Observer', '{"edit": false, "delete": false}'),
    (4, 'Developer', '{"edit": true, "delete": false}');

-- Вставка данных в таблицу board_users
INSERT INTO board_users (user_id, board_id)
VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4);

-- Вставка данных в таблицу user_roles
INSERT INTO user_roles (user_id, role_id)
VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4);

-- Вставка данных в таблицу task_labels
INSERT INTO task_labels (task_id, label_id)
VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4);



---- Серверное программирование Лаба 2
-- Генерация 200 записей для таблицы users
-- Генерация 200 записей для таблицы users
INSERT INTO users (username, usr_password)
SELECT
        'user' || generate_series(1, 200), -- Генерация уникальных имен пользователей
        md5(random()::text) -- Генерация случайных хэшей паролей
FROM generate_series(1, 200);

-- Генерация 200 записей для таблицы boards
INSERT INTO boards (title)
SELECT
        'Доска ' || generate_series(1, 200) -- Генерация уникальных названий досок
FROM generate_series(1, 200);

-- Генерация 200 записей для таблицы lists
INSERT INTO lists (title, list_position, board_id)
SELECT
        'Список ' || generate_series(1, 200), -- Генерация уникальных названий списков
        floor(random() * 10) + 1, -- Генерация случайных позиций списков
        floor(random() * 200) + 1 -- Генерация случайных идентификаторов досок
FROM generate_series(1, 200);

-- Генерация 200 записей для таблицы tasks
INSERT INTO tasks (task_text, task_position, list_id, due_date)
SELECT
        'Задача ' || generate_series(1, 200), -- Генерация уникальных текстов задач
        floor(random() * 10) + 1, -- Генерация случайных позиций задач
        floor(random() * 200) + 1, -- Генерация случайных идентификаторов списков
        CASE
            WHEN random() < 0.5 THEN NULL -- Пример: половина задач без срока выполнения
            ELSE CURRENT_DATE + floor(random() * 30) * INTERVAL '1 day' -- Пример: случайная дата в пределах 30 дней от текущей даты
            END
FROM generate_series(1, 200);

-- Генерация 200 записей для таблицы board_roles, по одной записи на каждую доску с одним пользователем и случайной ролью
INSERT INTO board_roles (user_id, board_id, role_id)
SELECT
    generate_series as current_number_1, -- Генерация user_id от 1 до 200
    generate_series as current_number_2, -- Генерация board_id от 1 до 200
    floor(random() * 4) + 1 -- Генерация случайного role_id (предполагается, что у вас есть 4 роли)
FROM generate_series(1, 200);

-- Очистка таблицы "board_roles"
DELETE FROM board_roles;

-- Очистка таблицы "labels"
DELETE FROM labels;

-- Очистка таблицы "comments"
DELETE FROM comments;

-- Очистка таблицы "roles"
DELETE FROM roles;

-- Очистка таблицы "tasks"
DELETE FROM tasks;

-- Очистка таблицы "lists"
DELETE FROM lists;

-- Очистка таблицы "boards"
DELETE FROM boards;

-- Очистка таблицы "users"
DELETE FROM users;
