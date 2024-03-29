-- (?s)CREATE TABLE\s+.*?;$

-- Создаем таблицу пользователей
CREATE TABLE IF NOT EXISTS users
(
    user_id      INT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY, -- Уникальный идентификатор пользователя
    username     VARCHAR(255) NOT NULL UNIQUE,                     -- Логин пользователя
    email        VARCHAR(255) NOT NULL,                            -- Почта пользователя
    usr_password VARCHAR(255) NOT NULL,                            -- Пароль пользователя
    preferences  jsonb                                             -- Индивидуальные настройки пользователя
);

COMMENT ON COLUMN users.user_id IS 'Уникальный идентификатор пользователя';
COMMENT ON COLUMN users.username IS 'Логин пользователя';
COMMENT ON COLUMN users.email IS 'Почта пользователя';
COMMENT ON COLUMN users.usr_password IS 'Пароль пользователя';
COMMENT ON COLUMN users.preferences IS 'Индивидуальные настройки пользователя';


-- Создаем таблицу досок
CREATE TABLE boards
(
    board_id  INT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY, -- Уникальный идентификатор доски
    title     VARCHAR(255) NOT NULL,                            -- Название доски
    last_edit TIMESTAMP                                         -- Время последнего изменения
);

COMMENT ON COLUMN boards.board_id IS 'Уникальный идентификатор доски';
COMMENT ON COLUMN boards.title IS 'Название доски';
COMMENT ON COLUMN boards.last_edit IS 'Время последнего изменения';


-- Создаем таблицу списков
CREATE TABLE lists
(
    list_id       INT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,      -- Уникальный идентификатор списка
    title         VARCHAR(255) NOT NULL,                                 -- Название списка
    list_position INTEGER,                                               -- Порядковый номер списка на доске
    board_id      INTEGER REFERENCES boards (board_id) ON DELETE CASCADE -- Ссылка на доску, на которой находится список
);



COMMENT ON COLUMN lists.list_id IS 'Уникальный идентификатор списка';
COMMENT ON COLUMN lists.title IS 'Название списка';
COMMENT ON COLUMN lists.list_position IS 'Порядковый номер списка на доске';
COMMENT ON COLUMN lists.board_id IS 'Ссылка на доску, на которой находится список';

ALTER TABLE lists
    ADD CONSTRAINT unique_list_position_on_board UNIQUE (list_position, board_id);


-- Создаем таблицу записей
CREATE TABLE tasks
(
    task_id          INT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,      -- Уникальный идентификатор записи
    task_text        TEXT,                                                  -- Текст записи
    task_position    INTEGER,                                               -- Порядковый номер записи в списке
    list_id          INTEGER REFERENCES lists (list_id) ON DELETE CASCADE,  -- Ссылка на список, в котором находится запись
    due_date         DATE,                                                  -- Срок выполнения записи
    task_doer_id     INTEGER REFERENCES users (user_id) ON DELETE SET NULL, -- Человек, назначенный на выполнение карточки
    task_preferences jsonb                                                  -- Дополнительные настройки карточки
);

COMMENT ON COLUMN tasks.task_id IS 'Уникальный идентификатор записи';
COMMENT ON COLUMN tasks.task_text IS 'Текст записи';
COMMENT ON COLUMN tasks.task_position IS 'Порядковый номер записи в списке';
COMMENT ON COLUMN tasks.list_id IS 'Ссылка на список, в котором находится запись';
COMMENT ON COLUMN tasks.due_date IS 'Срок выполнения записи';
COMMENT ON COLUMN tasks.task_doer_id IS 'Человек, назначенный на выполнение карточки';
COMMENT ON COLUMN tasks.task_preferences IS 'Дополнительные настройки карточки';


ALTER TABLE tasks
    ADD CONSTRAINT unique_task_position_list_id UNIQUE (task_position, list_id);

ALTER TABLE tasks
    ADD COLUMN task_title varchar;

COMMENT ON COLUMN tasks.task_title IS 'Название карточки';

-- Создаем таблицу меток
CREATE TABLE labels
(
    label_id   INT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY, -- Уникальный идентификатор метки
    title      VARCHAR(255),                                     -- Название метки
    label_info VARCHAR(255)                                      -- Информация о метке
);

COMMENT ON COLUMN labels.label_id IS 'Уникальный идентификатор метки';
COMMENT ON COLUMN labels.title IS 'Название метки';
COMMENT ON COLUMN labels.label_info IS 'Информация о метке';


-- Создаем таблицу комментариев
CREATE TABLE comments
(
    comment_id   INT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,      -- Уникальный идентификатор комментария
    task_id      INTEGER REFERENCES tasks (task_id) ON DELETE CASCADE,  -- Ссылка на запись, которую комментируют
    user_id      INTEGER REFERENCES users (user_id) ON DELETE SET NULL, -- Ссылка на пользователя, который оставил комментарий
    comment_text TEXT,                                                  -- Текст комментария
    comment_date TIMESTAMP                                              -- Дата и время комментария
);

COMMENT ON COLUMN comments.comment_id IS 'Уникальный идентификатор комментария';
COMMENT ON COLUMN comments.task_id IS 'Ссылка на запись, которую комментируют';
COMMENT ON COLUMN comments.user_id IS 'Ссылка на пользователя, который оставил комментарий';
COMMENT ON COLUMN comments.comment_text IS 'Текст комментария';
COMMENT ON COLUMN comments.comment_date IS 'Дата и время комментария';


-- Создаем таблицу ролей
CREATE TABLE roles
(
    role_id          INT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY, -- Уникальный идентификатор роли
    board_id         INTEGER REFERENCES boards (board_id) ON DELETE CASCADE ,             -- Ссылка на доску
    role_name        VARCHAR(50),                                      -- Название роли
    role_permissions jsonb                                             -- Права роли в формате JSONB
);

COMMENT ON COLUMN roles.role_id IS 'Уникальный идентификатор роли';
COMMENT ON COLUMN roles.board_id IS 'Ссылка на доску';
COMMENT ON COLUMN roles.role_name IS 'Название роли';
COMMENT ON COLUMN roles.role_permissions IS 'Права роли в формате JSONB';


-- Создаем таблицу связей между пользователями и досками
CREATE TABLE board_users
(
    user_id  INTEGER REFERENCES users (user_id) ON DELETE CASCADE,   -- Идентификатор пользователя, связанный с таблицей users
    board_id INTEGER REFERENCES boards (board_id) ON DELETE CASCADE, -- Идентификатор доски, связанный с таблицей boards
    PRIMARY KEY (user_id, board_id)
);

COMMENT ON COLUMN board_users.user_id IS 'Идентификатор пользователя';
COMMENT ON COLUMN board_users.board_id IS 'Идентификатор доски';


-- Создаем таблицу связей между пользователями и ролями
CREATE TABLE user_roles
(
    user_id INTEGER REFERENCES users (user_id) ON DELETE CASCADE, -- Идентификатор пользователя, связанный с таблицей users
    role_id INTEGER REFERENCES roles (role_id) ON DELETE CASCADE, -- Идентификатор роли, связанный с таблицей roles
    PRIMARY KEY (user_id, role_id)                                -- Определяем составной первичный ключ (user_id, role_id)
);

COMMENT ON COLUMN user_roles.user_id IS 'Идентификатор пользователя';
COMMENT ON COLUMN user_roles.role_id IS 'Идентификатор роли';


-- Создаем таблицу связей между задачами и метками
CREATE TABLE task_labels
(
    task_id  INTEGER REFERENCES tasks (task_id) ON DELETE CASCADE,   -- Идентификатор задачи, связанный с таблицей tasks
    label_id INTEGER REFERENCES labels (label_id) ON DELETE CASCADE, -- Идентификатор метки, связанный с таблицей labels
    PRIMARY KEY (task_id, label_id)                                  -- Определяем составной первичный ключ (task_id, label_id)
);

COMMENT ON COLUMN task_labels.task_id IS 'Идентификатор задачи';
COMMENT ON COLUMN task_labels.label_id IS 'Идентификатор метки';


-- DROP TABLE IF EXISTS task_labels CASCADE;
-- DROP TABLE IF EXISTS user_roles CASCADE;
-- DROP TABLE IF EXISTS board_users CASCADE;
-- DROP TABLE IF EXISTS roles CASCADE;
-- DROP TABLE IF EXISTS comments CASCADE;
-- DROP TABLE IF EXISTS labels CASCADE;
-- DROP TABLE IF EXISTS tasks CASCADE;
-- DROP TABLE IF EXISTS lists CASCADE;
-- DROP TABLE IF EXISTS boards CASCADE;
-- DROP TABLE IF EXISTS users CASCADE;


-- -- Вывод пользователя, названия доски и роли на доске
-- SELECT u.user_id,
--        u.username AS user_name,
--        b.board_id,
--        b.title    AS board_title,
--        r.role_id,
--        r.role_name
-- FROM users u
--          JOIN board_roles br ON u.user_id = br.user_id
--          JOIN boards b ON br.board_id = b.board_id
--          JOIN roles r ON br.role_id = r.role_id;
