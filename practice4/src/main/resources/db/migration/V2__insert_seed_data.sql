
INSERT INTO series (title, genre, release_year)
VALUES
    ('Game Of Thrones', 'drama', 2011),
    ('Dark', 'Sci-Fi', 2017),
    ('Sherlock', 'Mystery', 2010),
    ('The Last of Us', 'Drama', 2023);

INSERT INTO episodes (title, episode_number, series_id)
SELECT 'Winter is coming', 1, id
FROM series
WHERE title = 'Game Of Thrones';

INSERT INTO episodes (title, episode_number, series_id)
SELECT 'Secrets', 1, id
FROM series
WHERE title = 'Dark';

INSERT INTO episodes (title, episode_number, series_id)
SELECT 'A Study in Pink', 1, id
FROM series
WHERE title = 'Sherlock';

INSERT INTO episodes (title, episode_number, series_id)
SELECT 'When You Are Lost in the Darkness', 1, id
FROM series
WHERE title = 'The Last of Us';
