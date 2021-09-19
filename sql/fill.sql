INSERT INTO PERSON (NAME, HEIGHT, PASSPORT_ID, NATIONALITY)
VALUES ('Hayao Miyazaki', 164, 'abc', 'JAPAN'),
       ('Makoto Shinkai', 160, 'bcd', 'JAPAN'),
       ('Peter Jackson', 165, 'cde', 'GERMANY'),
       ('Frank Darabont', 186, 'def', 'SPAIN');

INSERT INTO COORDINATES (X, Y)
VALUES (0, 0),
       (100, 100),
       (200, 200),
       (300, 300),
       (400, 400),
       (500, 500),
       (600, 600),
       (700, 700),
       (800, 800),
       (900, 900);

INSERT INTO MOVIE (NAME, COORDINATES, CREATION_DATE, OSCARS_COUNT, GOLDEN_PALM_COUNT, TOTAL_BOX_OFFICE, MPAA_RATING, SCREEN_WRITER)
VALUES ('Howl''s Moving Castle', 5, '2004-01-01', 2, 2, 236214446, 'PG', 1),
       ('The Lord of the Rings: The Return of the King', 1, '2003-01-01', 11, 5, 1140682011, 'PG_13', null),
       ('Sen and Chihiro''s Spiriting Away', 6, '2004-01-01', 3, 3, 355467076, 'PG', 1);
