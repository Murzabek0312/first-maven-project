INSERT INTO actor(id, firstname, secondname, birth_date, biography)
VALUES (1, 'James', 'Bond', '2012-5-13', 'Agent007'),
       (2, 'actorName1', 'astorSecondName1', '2012-5-13', 'actorBiography1'),
       (3, 'Quentin', 'Tarantino', '1963-3-27', 'Cool boy'),
       (4, 'Petr', 'actorSecondName1', '2012-7-5', 'actorBiography1'),
       (5, 'Petr', 'actorSecondName2', '2013-10-14', 'actorBiography2'),
       (6, 'Dmitriy', 'astorSecondName3', '2012-5-27', 'actorBiography3'),
       (7, 'Quentin', 'NeTarantino', '1966-4-30', 'Bad boy');

INSERT INTO subscription(id, status, type)
VALUES (1, 'ACTIVE', 'PREMIUM'),
       (2, 'EXPIRED', 'STANDART');

INSERT INTO movie(id, name, director_id, country, genre, subscription_id, release_date)
VALUES (1, 'The Hateful Eight', 3, 'USA', 'ACTION', 1, '2016-1-1'),
       (2, 'movieName', 3, 'USA', 'TRILLER', 1, '2022-2-12'),
       (3, 'The Pursuit of Happyness', 3, 'USA', 'DRAMA', 1, '2016-1-1'),
       (4, 'Gone with the Wind', 3, 'USA', 'DRAMA', 1, '2016-1-1');

INSERT INTO movie_actor(id, actor_id, movie_id)
VALUES (1, 4, 1),
       (2, 5, 1);

INSERT INTO users(id, username, first_name, second_name, password, email, subscription_id)
VALUES (1, 'username', 'firstName', 'secondName', 'password', 'email', 1),
       (2, 'username2', 'firstName2', 'secondName2', 'password2', 'email2', 1),
       (3, 'username3', 'firstName3', 'secondName3', 'password3', 'email3', 1);

INSERT INTO feedback(id, movie_id, comment, rating, user_id)
VALUES (1, 2, 'comment', 'EXCELLENT', 2),
       (2, 2, 'comment2', 'NORM', 2),
       (3, 2, 'comment3', 'BAD', 3)