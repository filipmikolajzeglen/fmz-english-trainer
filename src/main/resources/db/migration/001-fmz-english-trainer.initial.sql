CREATE SCHEMA translation;
CREATE SEQUENCE translation.irregular_verb_seq;
CREATE TABLE translation.irregular_verb
(
   id                        BIGINT                NOT NULL PRIMARY KEY,
   i_form                    VARCHAR(255)          NOT NULL,
   ii_form                   VARCHAR(255)          NOT NULL,
   iii_form                  VARCHAR(255)          NOT NULL,
   number_of_mistakes        INTEGER DEFAULT 0     NOT NULL,
   number_of_repetitions     INTEGER DEFAULT 0     NOT NULL,
   is_taught                 BOOLEAN DEFAULT FALSE NOT NULL,
   translation               VARCHAR(255)          NOT NULL,
   number_of_correct_answers INTEGER DEFAULT 0
);

COMMENT ON TABLE translation.irregular_verb IS 'Czasowniki nieregularne ze wszystkimi formami i tłumaczeniem';
COMMENT ON COLUMN translation.irregular_verb.id IS 'Identyfikator';
COMMENT ON COLUMN translation.irregular_verb.i_form IS 'Pierwsza forma czasownika nieregularnego';
COMMENT ON COLUMN translation.irregular_verb.ii_form IS 'Druga forma czasownika nieregularnego';
COMMENT ON COLUMN translation.irregular_verb.iii_form IS 'Trzecia forma czasownika nieregularnego';
COMMENT ON COLUMN translation.irregular_verb.translation IS 'Tłumaczenie na język polski';

-- Pozostałe kolumny zostaną w kolejnych patach'ach wydzielone do osobnej tabeli, która będzie wiązać wynik nauki
-- z konkretnym użytkownikiem aplikacji. To jest uproszczona wersja na potrzeby testów flyway.

CREATE SEQUENCE translation.irregular_verb_grade_seq;
CREATE TABLE translation.irregular_verb_grade
(
   id              BIGINT                   NOT NULL PRIMARY KEY,
   grade           VARCHAR(255)             NOT NULL,
   date            TIMESTAMP WITH TIME ZONE NOT NULL,
   wrong_answers   INTEGER DEFAULT 0        NOT NULL,
   correct_answers INTEGER DEFAULT 0        NOT NULL
);

COMMENT ON TABLE translation.irregular_verb_grade IS 'Oceny testów i egzaminów dla czasowników nieregularne';
COMMENT ON COLUMN translation.irregular_verb_grade.id IS 'Identyfikator';
COMMENT ON COLUMN translation.irregular_verb_grade.grade IS 'Ocena';
COMMENT ON COLUMN translation.irregular_verb_grade.date IS 'Data otrzymania oceny';
COMMENT ON COLUMN translation.irregular_verb_grade.wrong_answers IS 'Ilość poprawnych odpowiedzi';
COMMENT ON COLUMN translation.irregular_verb_grade.correct_answers IS 'Ilość niepoprawnych odpowiedzi';

-- Powyższa logika zostanie zmodyfikowana w kolejnych patach'ach do postaci agregującej oceny ze wszystkich rodzajów
-- testów i egzaminów dla każdego tematu. To jest uproszczona wersja na potrzeby testów flyway.