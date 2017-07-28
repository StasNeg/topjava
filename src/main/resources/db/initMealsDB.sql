DROP TABLE IF EXISTS meals;
DROP SEQUENCE IF EXISTS global_seq_meal;

CREATE SEQUENCE global_seq_meal START 100000;

CREATE TABLE meals
(
  id          INTEGER PRIMARY KEY DEFAULT nextval('global_seq_meal'),
  dateTime    TIMESTAMP           DEFAULT now(),
  description VARCHAR           NOT NULL,
  calories    INTEGER DEFAULT 0 NOT NULL,
  idUser      INTEGER           NOT NULL
);

CREATE UNIQUE INDEX meals_unique_time_idx
  ON meals (dateTime);


