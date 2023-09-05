CREATE TABLE `book` (
    isbn BIGINT,
    title VARCHAR(255),
    author VARCHAR(255),
    quantity INT,
    status ENUM(
        'AVAILABLE',
        'BORROWED',
        'LOST'
        ),
    PRIMARY KEY (isbn)
);

CREATE TABLE `member` (
    id INT AUTO_INCREMENT,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    gender VARCHAR(255),
    membership_number INT,
    PRIMARY KEY (id)
);

CREATE TABLE `reservation` (
    id INT AUTO_INCREMENT,
    isbn BIGINT,
    member_id INT,
    borrowing_date DATE,
    has_been_returned BOOLEAN,
    PRIMARY KEY (id),
    FOREIGN KEY (isbn) REFERENCES book(isbn),
    FOREIGN KEY (member_id) REFERENCES member(id)
);

CREATE TABLE `lost_book` (
    id INT AUTO_INCREMENT,
    isbn BIGINT,
    quantity INT,
    PRIMARY KEY (id),
    FOREIGN KEY (isbn) REFERENCES book(isbn)
);

INSERT INTO `book` (
    isbn,
    title,
    author,
    quantity,
    status
) VALUES (
    9783104027098,
    'Gone Girl',
    'Gillian Flynn',
    15,
    'AVAILABLE'
);

INSERT INTO `book` (
    isbn,
    title,
    author,
    quantity,
    status
) VALUES (
    9788415957034,
    'Crime and Punishment',
    'Fyodor Dostoevsky',
    5,
    'AVAILABLE'
);

INSERT INTO `book` (
    isbn,
    title,
    author,
    quantity,
    status
) VALUES (
    9783104012544,
    'Meditations',
    'Marcus Aurelius',
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

INSERT INTO `reservation` (
    isbn,
    member_id,
    borrowing_date,
    has_been_returned
) VALUES (
    9783104012544,
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
    9783104012544,
    2,
    '2023-09-01',
    false
);
