1. Создание таблицы
```
create table default.artist (
    mbid string,
    artist_mb string,
    artist_lastfm string,
    country_mb string,
    country_lastfm string,
    tags_mb array <string>,
    tags_lastfm array <string>,
    listeners_lastfm int,
    scrobbles_lastfm int,
    ambiguous_artist boolean
);
```
2. Исполнитель с максимальным числом скробблов
```
with tmp_table as (
    SELECT max(scrobbles_lastfm)
    FROM artist
)

SELECT artist_lastfm
FROM artist
WHERE scrobbles_lastfm IN ( select * from tmp_table)
```
artist_lastfm
The Beatles
3. Самый популрный тэг на lastfm
```
with tmp_table as (
    SELECT
        trim(tag) AS trim_tag,
        count(*) AS cnt_tag
    FROM artist LATERAL VIEW explode(tags_lastfm) t AS tag
    WHERE trim(tag) != ''
    GROUP BY trim(tag)
    ORDER BY cnt_tag DESC
    LIMIT 1
)

SELECT trim_tag FROM tmp_table
```
trim_tag
seen live
4. Самые популярные исполнители 10 самых популярных тегов
```
with tmp_artists as (
    SELECT
        artist_lastfm,
        trim(tag) as tag,
        scrobbles_lastfm
    FROM artist LATERAL VIEW explode(tags_lastfm) t AS tag
    WHERE
        scrobbles_lastfm IS NOT NULL
        AND trim(tag) != ''
), tmp_tags as (
    SELECT
        trim(tag) AS tag,
        count(*) AS cnt_tag
    FROM artist LATERAL VIEW explode(tags_lastfm) t AS tag
    WHERE
        trim(tag) != ''
    GROUP BY trim(tag)
    ORDER BY cnt_tag DESC
    LIMIT 10
)

SELECT
    DISTINCT artist_lastfm,
    scrobbles_lastfm
FROM tmp_artists
WHERE tag IN ( SELECT tag FROM tmp_tags)
ORDER BY scrobbles_lastfm DESC
LIMIT 10
```
artist_lastfm	scrobbles_lastfm
The Beatles	517126254
Radiohead	499548797
Coldplay	360111850
Muse	344838631
Arctic Monkeys	332306552
Pink Floyd	313236119
Linkin Park	294986508
Red Hot Chili Peppers	293784041
Lady Gaga	285469647
Metallica	281172228
5. Самые популярные исполнители в России 10 самых популярных тегов 
```
with tmp_artists as (
    SELECT
        artist_lastfm,
        trim(tag) as tag,
        scrobbles_lastfm
    FROM artist LATERAL VIEW explode(tags_lastfm) t AS tag
    WHERE
        scrobbles_lastfm IS NOT NULL
        AND trim(tag) != ''
        AND country_lastfm = 'Russia'
), tmp_tags as (
    SELECT
        trim(tag) AS tag,
        count(*) AS cnt_tag
    FROM artist LATERAL VIEW explode(tags_lastfm) t AS tag
    WHERE
        trim(tag) != '' 
        AND country_lastfm = 'Russia'
    GROUP BY trim(tag)
    ORDER BY cnt_tag DESC
    LIMIT 10
)

SELECT
    DISTINCT artist_lastfm,
    scrobbles_lastfm
FROM tmp_artists
WHERE tag IN ( SELECT tag FROM tmp_tags)
ORDER BY scrobbles_lastfm DESC
LIMIT 10
```
artist_lastfm	scrobbles_lastfm
Сплин	30642848
t.A.T.u.	24282244
Zемфира	22106841
Noize MC	20772736
Король и Шут	18910852
Lumen	17579969
Pyotr Ilyich Tchaikovsky	14454743
Кино	14012892
Ария	13728661
Ленинград	12989989