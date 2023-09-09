CREATE TABLE `member` (
    id INT AUTO_INCREMENT,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    gender VARCHAR(255),
    membership_number INT,
    PRIMARY KEY (id)
);

CREATE TABLE `author` (
    id INT AUTO_INCREMENT,
    name VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE `book` (
    isbn VARCHAR(255) UNIQUE,
    title VARCHAR(255),
    author_id INT,
    quantity INT,
    status ENUM(
        'AVAILABLE',
        'BORROWED',
        'LOST'
        ),
    PRIMARY KEY (isbn),
    FOREIGN KEY (author_id) REFERENCES author(id)
);

CREATE TABLE `reservation` (
    id INT AUTO_INCREMENT,
    isbn VARCHAR(255),
    member_id INT,
    borrowing_date DATE,
    has_been_returned BOOLEAN,
    PRIMARY KEY (id),
    FOREIGN KEY (isbn) REFERENCES book(isbn),
    FOREIGN KEY (member_id) REFERENCES member(id)
);

CREATE TABLE `lost_book` (
    id INT AUTO_INCREMENT,
    isbn VARCHAR(255),
    quantity INT,
    PRIMARY KEY (id),
    FOREIGN KEY (isbn) REFERENCES book(isbn)
);

DELIMITER $$
$$
CREATE PROCEDURE `LibroSync`.`InsertAndReturn`(
    IN table_name VARCHAR(255),
    IN column_names VARCHAR(255),
    IN column_values VARCHAR(255)
)
BEGIN
    SET @sql_query = CONCAT('INSERT INTO ', table_name, ' (', column_names, ') VALUES (', column_values, ')');

    PREPARE stmt FROM @sql_query;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;

    IF table_name = 'book' THEN
        SET @primary_key_value = (SELECT SUBSTRING_INDEX(column_values, ',', 1));
        SET @select_query = CONCAT('SELECT * FROM ', table_name, ' WHERE isbn = ', @primary_key_value);

    ELSE
        SET @primary_key_value = LAST_INSERT_ID();
        SET @select_query = CONCAT('SELECT * FROM ', table_name, ' WHERE id = @primary_key_value');
    END IF;

    PREPARE stmt FROM @select_query;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;

END$$
DELIMITER ;


DELIMITER //
CREATE TRIGGER before_reservation_insert
    BEFORE INSERT ON reservation
    FOR EACH ROW
BEGIN
    IF NEW.borrowing_date > CURDATE() THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Cannot insert reservation with borrowing date in the future';
END IF;
END;
//
DELIMITER ;

INSERT INTO `author` (
    name
) VALUES (
    'Fyodor Dostoevsky'
);

INSERT INTO `author` (
    name
) VALUES (
    'Gillian Flynn'
);

INSERT INTO `author` (
    name
) VALUES (
    'Marcus Aurelius'
);

INSERT INTO `book` (
    isbn,
    title,
    author_id,
    quantity,
    status
) VALUES (
    '9783104027098',
    'Gone Girl',
    2,
    15,
    'AVAILABLE'
);

INSERT INTO `book` (
    isbn,
    title,
    author_id,
    quantity,
    status
) VALUES (
    '9788415957034',
    'Crime and Punishment',
    1,
    5,
    'AVAILABLE'
);

INSERT INTO `book` (
    isbn,
    title,
    author_id,
    quantity,
    status
) VALUES (
    '9783104012544',
    'Meditations',
    3,
    0,
    'BORROWED'
);

INSERT INTO `member` (
    first_name,
    last_name,
    gender,
    membership_number
) VALUES (
    'Issam',
    'Mezgueldi',
    'Male',
    1001
);

INSERT INTO `member` (
    first_name,
    last_name,
    gender,
    membership_number
) VALUES (
    'Jhon',
    'Snow',
    'Male',
    1002
);

INSERT INTO `reservation` (
    isbn,
    member_id,
    borrowing_date,
    has_been_returned
) VALUES (
    '9783104012544',
    1,
    '2023-09-03',
    false
);

INSERT INTO `reservation` (
    isbn,
    member_id,
    borrowing_date,
    has_been_returned
) VALUES (
    '9783104012544',
    2,
    '2023-09-01',
    false
);

INSERT INTO `lost_book` (
    isbn,
    quantity
) VALUES (
    '9783104012544',
    1
);
