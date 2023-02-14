CREATE SCHEMA IF NOT EXISTS tyva_schema;

DROP TABLE IF EXISTS tyva_schema.as_object_levels CASCADE;
DROP TABLE IF EXISTS tyva_schema.as_addr_obj_types CASCADE;
DROP TABLE IF EXISTS tyva_schema.as_param_types CASCADE;
DROP TABLE IF EXISTS tyva_schema.as_house_types CASCADE;
DROP TABLE IF EXISTS tyva_schema.as_reestr_objects CASCADE;

CREATE TABLE IF NOT EXISTS tyva_schema.as_object_levels
-- Состав и структура файла со сведениями по уровням адресных объектов. Сведения по уровням адресных объектов.
(
    LEVEL      SMALLINT     NOT NULL PRIMARY KEY, -- Уникальный идентификатор записи. Ключевое поле. Номер уровня объекта
    NAME       VARCHAR(250) NOT NULL,             -- Наименование
    SHORTNAME  VARCHAR(50),                       -- Краткое наименование
    UPDATEDATE DATE         NOT NULL,             -- Дата внесения (обновления) записи
    STARTDATE  DATE         NOT NULL,             -- Начало действия записи
    ENDDATE    DATE         NOT NULL,             -- Окончание действия записи
    ISACTIVE   BOOLEAN      NOT NULL              -- Признак действующего адресного объекта
);

CREATE TABLE IF NOT EXISTS tyva_schema.as_addr_obj_types
-- Состав и структура файла со сведениями по типам адресных объектов. Сведения по типам адресных объектов.
(
    ID          SMALLINT     NOT NULL PRIMARY KEY,                                                       -- Идентификатор записи
    LEVEL       INTEGER      NOT NULL REFERENCES tyva_schema.as_object_levels (LEVEL) ON DELETE CASCADE, -- Уровень адресного объекта
    SHORTNAME   VARCHAR(50)  NOT NULL,                                                                   -- Краткое наименование типа объекта
    NAME        VARCHAR(250) NOT NULL,                                                                   -- Полное наименование типа объекта
    DESCRIPTION VARCHAR(250),                                                                            -- Описание
    UPDATEDATE  DATE         NOT NULL,                                                                   -- Дата внесения (обновления) записи
    STARTDATE   DATE         NOT NULL,                                                                   -- Начало действия записи
    ENDDATE     DATE         NOT NULL,                                                                   -- Окончание действия записи
    ISACTIVE    BOOLEAN      NOT NULL                                                                    -- Статус активности
);

CREATE TABLE IF NOT EXISTS tyva_schema.as_param_types
-- Состав и структура файла с типами параметров. Сведения по типу параметра.
(
    ID          SMALLINT    NOT NULL PRIMARY KEY, -- Идентификатор типа параметра (ключ)
    NAME        VARCHAR(50) NOT NULL,             -- Наименование
    CODE        VARCHAR(50) NOT NULL,             -- Краткое наименование
    DESCRIPTION VARCHAR(120),                     -- Описание
    UPDATEDATE  DATE        NOT NULL,             -- Дата внесения (обновления) записи
    STARTDATE   DATE        NOT NULL,             -- Начало действия записи
    ENDDATE     DATE        NOT NULL,             -- Окончание действия записи
    ISACTIVE    BOOLEAN     NOT NULL              -- Статус активности
);

CREATE TABLE IF NOT EXISTS tyva_schema.as_house_types
-- Состав и структура файла со сведениями по типам домов. Сведения по типам домов.
(
    ID          SMALLINT    NOT NULL PRIMARY KEY, -- Идентификатор
    NAME        VARCHAR(50) NOT NULL,             -- Наименование
    SHORTNAME   VARCHAR(50),                      -- Краткое наименование
    DESCRIPTION VARCHAR(250),                     -- Описание
    UPDATEDATE  DATE        NOT NULL,             -- Дата внесения (обновления) записи
    STARTDATE   DATE        NOT NULL,             -- Начало действия записи
    ENDDATEDATE DATE        NOT NULL,             -- Окончание действия записи
    ISACTIVE    BOOLEAN     NOT NULL              -- Статус активности
);

CREATE TABLE IF NOT EXISTS tyva_schema.as_reestr_objects
-- Состав и структура файла со сведениями о реестре GUID объектов
(
    OBJECTID   BIGINT      NOT NULL PRIMARY KEY,                                                       -- Уникальный идентификатор объекта
    OBJECTGUID VARCHAR(36) NOT NULL,                                                                   -- GUID объекта
    CHANGEID   BIGINT      NOT NULL,                                                                   -- ID изменившей транзакции
    ISACTIVE   SMALLINT    NOT NULL,                                                                   -- Признак действующего объекта (1 - действующий, 0 - не действующий
    LEVELID    SMALLINT    NOT NULL REFERENCES tyva_schema.as_object_levels (LEVEL) ON DELETE CASCADE, -- Уровень объекта
    CREATEDATE DATE        NOT NULL,                                                                   -- Дата создания
    UPDATEDATE DATE        NOT NULL                                                                    -- Дата обновления
);

CREATE TABLE IF NOT EXISTS tyva_schema.as_addr_obj
-- Состав и структура файла со сведениями классификатора адресообразующих элементов БД ФИАС. Сведения классификатора адресообразующих элементов.
(
    ID         BIGINT       NOT NULL PRIMARY KEY,                                                           -- Уникальный идентификатор записи. Ключевое поле
    OBJECTID   BIGINT       NOT NULL REFERENCES tyva_schema.as_reestr_objects (OBJECTID) ON DELETE CASCADE, -- Глобальный уникальный идентификатор объекта типа INTEGER
    OBJECTGUID VARCHAR(36)  NOT NULL,                                                                       -- Глобальный уникальный идентификатор адресного объекта типа UUID. Совпадает с AOGUID в формате выгрузки ФИАС
    CHANGEID   BIGINT       NOT NULL,                                                                       -- ID изменившей транзакции
    NAME       VARCHAR(250) NOT NULL,                                                                       -- Наименование
    TYPENAME   VARCHAR(50)  NOT NULL,                                                                       -- Краткое наименование типа объекта
    LEVEL      VARCHAR(10)  NOT NULL,                                                                       -- Уровень адресного объекта
    OPERTYPEID BIGINT       NOT NULL,                                                                       -- Статус действия над записью – причина появления записи. Значения приведены в описании формата AS_OPERATION.TYPES_2_251_14
    PREVID     BIGINT,                                                                                      -- Идентификатор записи связывания с предыдущей исторической записью
    NEXTID     BIGINT,                                                                                      -- Идентификатор записи связывания с последующей исторической записью
    UPDATEDATE DATE         NOT NULL,                                                                       -- Дата внесения (обновления) записи
    STARTDATE  DATE         NOT NULL,                                                                       -- Начало действия записи
    ENDDATE    DATE         NOT NULL,                                                                       -- Окончание действия записи
    ISACTUAL   SMALLINT     NOT NULL,                                                                       -- Статус актуальности адресного объекта ФИАС, принимает значение: 0 – не актуальный | 1 – актуальный У последней записи адресного объекта элемент всегда принимает значение 1, у предыдущих – значение 0
    ISACTIVE   SMALLINT     NOT NULL                                                                        -- Признак действующего адресного объекта, принимает значение: 0 – недействующий адресный объект | 1 – действующий
);

CREATE INDEX IF NOT EXISTS tyva_schema_as_addr_obj_name_typename_idx
    ON tyva_schema.as_addr_obj USING gin (setweight(to_tsvector('russian', tyva_schema.as_addr_obj.name), 'A'),
                                          setweight(to_tsvector('russian', tyva_schema.as_addr_obj.typename), 'B'));

CREATE TABLE IF NOT EXISTS tyva_schema.as_mun_hierarchy
-- Состав и структура файла со сведениями по иерархии в муниципальном делении. Сведения по иерархии в муниципальном делении.
(
    ID          BIGINT       NOT NULL PRIMARY KEY,                                                           -- Уникальный идентификатор записи. Ключевое поле.
    OBJECTID    BIGINT       NOT NULL REFERENCES tyva_schema.as_reestr_objects (OBJECTID) ON DELETE CASCADE, -- Глобальный уникальный идентификатор адресного объекта.
    PARENTOBJID BIGINT,                                                                                      -- Идентификатор родительского объекта.
    CHANGEID    BIGINT       NOT NULL,                                                                       -- ID изменившей транзакции.
    OKTMO       VARCHAR(11),                                                                                 -- Код ОКТМО.
    PREVID      BIGINT,                                                                                      -- Идентификатор записи связывания с предыдущей исторической записью.
    NEXTID      BIGINT,                                                                                      -- Идентификатор записи связывания с последующей исторической записью.
    UPDATEDATE  DATE         NOT NULL,                                                                       -- Дата внесения (обновления) записи.
    STARTDATE   DATE         NOT NULL,                                                                       -- Начало действия записи.
    ENDDATE     DATE         NOT NULL,                                                                       -- Окончание действия записи.
    ISACTIVE    SMALLINT     NOT NULL,                                                                       -- Признак действующего адресного объекта.
    PATH        VARCHAR(250) NOT NULL                                                                        -- Материализованный путь к объекту (полная иерархия).
);
CREATE TABLE IF NOT EXISTS tyva_schema.as_adm_hierarchy
-- Состав и структура файла со сведениями по иерархии в административном делении. Сведения по иерархии в административном делении.
(
    ID          BIGINT       NOT NULL PRIMARY KEY,                                                           -- Уникальный идентификатор записи. Ключевое поле
    OBJECTID    BIGINT       NOT NULL REFERENCES tyva_schema.as_reestr_objects (OBJECTID) ON DELETE CASCADE, -- Глобальный уникальный идентификатор объекта
    PARENTOBJID BIGINT,                                                                                      -- Идентификатор родительского объекта
    CHANGEID    BIGINT       NOT NULL,                                                                       -- ID изменившей транзакции
    REGIONCODE  VARCHAR(4),                                                                                  -- Код региона
    AREACODE    VARCHAR(4),                                                                                  -- Код района
    CITYCODE    VARCHAR(4),                                                                                  -- Код города
    PLACECODE   VARCHAR(4),                                                                                  -- Код населенного пункта
    PLANCODE    VARCHAR(4),                                                                                  -- Код ЭПС
    STREETCODE  SMALLINT,                                                                                    -- Код улицы
    PREVID      INTEGER,                                                                                     -- Идентификатор записи связывания с предыдущей исторической записью
    NEXTID      INTEGER,                                                                                     -- Идентификатор записи связывания с последующей исторической записью
    UPDATEDATE  DATE         NOT NULL,                                                                       -- Дата внесения (обновления) записи
    STARTDATE   DATE         NOT NULL,                                                                       -- Начало действия записи
    ENDDATE     DATE         NOT NULL,                                                                       -- Окончание действия записи
    ISACTIVE    SMALLINT     NOT NULL,                                                                       -- Признак действующего адресного объекта, принимает значение: 0 – недействующий адресный объект | 1 – действующий
    PATH        VARCHAR(250) NOT NULL                                                                        -- Материализованный путь к объекту (полная иерархия) длина INTEGER в документайии ?
);

CREATE TABLE IF NOT EXISTS tyva_schema.as_steads
-- Состав и структура файла со сведениями по земельным участкам. Сведения по земельным участкам.
(
    ID         BIGINT       NOT NULL PRIMARY KEY,                                                           -- Уникальный идентификатор записи. Ключевое поле
    OBJECTID   BIGINT       NOT NULL REFERENCES tyva_schema.as_reestr_objects (OBJECTID) ON DELETE CASCADE, -- Глобальный уникальный идентификатор объекта типа INTEGER
    OBJECTGUID VARCHAR(36)  NOT NULL,                                                                       -- Глобальный уникальный идентификатор адресного объекта типа UUID
    CHANGEID   BIGINT       NOT NULL,                                                                       -- ID изменившей транзакции
    NUMBER     VARCHAR(250) NOT NULL,                                                                       -- Номер земельного участка
    OPERTYPEID SMALLINT     NOT NULL,                                                                       -- Статус действия над записью – причина появления записи
    PREVID     INTEGER,                                                                                     -- Идентификатор записи связывания с предыдущей исторической записью
    NEXTID     INTEGER,                                                                                     -- Идентификатор записи связывания с последующей исторической записью
    UPDATEDATE DATE         NOT NULL,                                                                       -- Дата внесения (обновления) записи
    STARTDATE  DATE         NOT NULL,                                                                       -- Начало действия записи
    ENDDATE    DATE         NOT NULL,                                                                       -- Окончание действия записи
    ISACTUAL   SMALLINT     NOT NULL,                                                                       -- Статус актуальности адресного объекта ФИАС
    ISACTIVE   SMALLINT     NOT NULL                                                                        -- Признак действующего адресного объекта
);

CREATE TABLE IF NOT EXISTS tyva_schema.as_houses
-- Состав и структура файла со сведениями по номерам домов улиц городов и населенных пунктов. Сведения по номерам домов улиц городов и населенных пунктов.
(
    ID         BIGINT      NOT NULL PRIMARY KEY,                                                           -- Уникальный идентификатор записи. Ключевое поле
    OBJECTID   BIGINT      NOT NULL REFERENCES tyva_schema.as_reestr_objects (OBJECTID) ON DELETE CASCADE, -- Глобальный уникальный идентификатор объекта типа INTEGER
    OBJECTGUID VARCHAR(36) NOT NULL,                                                                       -- Глобальный уникальный идентификатор адресного объекта типа UUID
    CHANGEID   BIGINT      NOT NULL,                                                                       -- ID изменившей транзакции
    HOUSENUM   VARCHAR(50) NOT NULL,                                                                       -- Основной номер дома
    HOUSETYPE  INTEGER REFERENCES tyva_schema.as_house_types (ID) ON DELETE CASCADE,                       -- Основной тип дома
    OPERTYPEID SMALLINT    NOT NULL,                                                                       -- Статус действия над записью – причина появления записи
    PREVID     BIGINT,                                                                                     -- Идентификатор записи связывания с предыдущей исторической записью
    NEXTID     BIGINT,                                                                                     -- Идентификатор записи связывания с последующей исторической записью
    UPDATEDATE DATE        NOT NULL,                                                                       -- Дата внесения (обновления) записи
    STARTDATE  DATE        NOT NULL,                                                                       -- Начало действия записи
    ENDDATE    DATE        NOT NULL,                                                                       -- Окончание действия записи
    ISACTUAL   SMALLINT    NOT NULL,                                                                       -- Статус актуальности адресного объекта ФИАС
    ISACTIVE   SMALLINT    NOT NULL,                                                                       -- Признак действующего адресного объекта
    ADDNUM1    VARCHAR(50),                                                                                -- Дополнительный номер дома 1
    ADDTYPE1   INTEGER,                                                                                    -- Дополнительный тип дома 1
    ADDNUM2    VARCHAR(50),                                                                                -- Дополнительный номер дома 2
    ADDTYPE2   INTEGER                                                                                     -- Дополнительный тип дома 2
);

CREATE EXTENSION IF NOT EXISTS pg_trgm;

CREATE INDEX IF NOT EXISTS tyva_schema_as_addr_obj_on_name_idx ON tyva_schema.as_addr_obj USING gin(name gin_trgm_ops);

CREATE INDEX IF NOT EXISTS tyva_schema_as_addr_obj_on_name_and_typename_idx ON tyva_schema.as_addr_obj USING gin(name gin_trgm_ops, typename gin_trgm_ops);

-- CREATE TABLE IF NOT EXISTS tyva_schema.words AS SELECT word FROM ts_stat('SELECT to_tsvector(''simple'', aao.name) FROM tyva_schema.as_addr_obj aao');
--
-- CREATE INDEX IF NOT EXISTS words_idx ON tyva_schema.words USING gin (word gin_trgm_ops);
