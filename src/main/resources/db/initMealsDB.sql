DROP TABLE IF EXISTS meals;
DROP SEQUENCE IF EXISTS global_seq_meal;

CREATE SEQUENCE global_seq_meal START 100000;

CREATE TABLE meals
(
  id          INTEGER PRIMARY KEY DEFAULT nextval('global_seq_meal'),
  datetime    TIMESTAMP           DEFAULT now(),
  description VARCHAR           NOT NULL,
  calories    INTEGER DEFAULT 0 NOT NULL,
  iduser      INTEGER           NOT NULL
);

CREATE INDEX meals_idx_datetime_idUsers ON meals (datetime,idUser);

