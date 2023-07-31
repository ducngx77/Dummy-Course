--
use DummiesCourse
-- Mock data for CourseType table
INSERT INTO CourseType (TypeName)
VALUES ('Science'),
       ('Mathematics'),
       ('History'),
       ('Literature'),
       ('Computer Science');


-- Mock data for Users table
INSERT INTO Users (Username, Email, Password, PhoneNumber, bio, Address, Gender, Status)
VALUES ('JohnDoe', 'johndoe@example.com', 'password123', '1234567890', 'Hello, I am John Doe.', '123 Main St', 1, 1),
       ('sannnh', 'sannnhhe176209@fpt.edu.vn', 'Aria4710', '1234567890', 'Hello, I am John Doe.', '123 Main St', 1, 1),
       ('JaneSmith', 'janesmith@example.com', 'password456', '9876543210', 'Hi, I am Jane Smith.', '456 Elm St', 2, 1),
       ('DavidJohnson', 'davidjohnson@example.com', 'password789', '5555555555', 'Nice to meet you!', '789 Oak St', 1, 1),
       ('supersanta', 'otakuaria4710@gmail.com', 'Aria4710', '1029384756', 'I am san', 'Thach That', 2, 1),
       ('santasan', 'otakuaria4710@gmail.com', 'Aria4710', '1029384756', 'I am san', 'Thach That', 2, 1);

INSERT INTO Users (Username, Email, Password, PhoneNumber, bio, Address, Gender, Status)
VALUES
    ('Lecturer3', 'lecturer3@example.com', 'password123', '1234567890', 'Hello, I am Lecturer3.', '123 Main St', 1, 1),
    ('Lecturer4', 'lecturer4@example.com', 'password123', '1234567890', 'Hello, I am Lecturer4.', '123 Main St', 1, 1),
    ('Lecturer5', 'lecturer5@example.com', 'password123', '1234567890', 'Hello, I am Lecturer5.', '123 Main St', 1, 1),
    ('Lecturer6', 'lecturer6@example.com', 'password123', '1234567890', 'Hello, I am Lecturer6.', '123 Main St', 1, 1),
    ('Lecturer7', 'lecturer7@example.com', 'password123', '1234567890', 'Hello, I am Lecturer7.', '123 Main St', 1, 1),
    ('Lecturer8', 'lecturer8@example.com', 'password123', '1234567890', 'Hello, I am Lecturer8.', '123 Main St', 1, 1),
    ('Lecturer9', 'lecturer9@example.com', 'password123', '1234567890', 'Hello, I am Lecturer9.', '123 Main St', 1, 1),
    ('Lecturer10', 'lecturer10@example.com', 'password123', '1234567890', 'Hello, I am Lecturer10.', '123 Main St', 1, 1),
    ('Lecturer11', 'lecturer11@example.com', 'password123', '1234567890', 'Hello, I am Lecturer11.', '123 Main St', 1, 1),
    ('Lecturer12', 'lecturer12@example.com', 'password123', '1234567890', 'Hello, I am Lecturer12.', '123 Main St', 1, 1),
    ('Lecturer13', 'lecturer13@example.com', 'password123', '1234567890', 'Hello, I am Lecturer13.', '123 Main St', 1, 1),
    ('Lecturer14', 'lecturer14@example.com', 'password123', '1234567890', 'Hello, I am Lecturer14.', '123 Main St', 1, 1),
    ('Lecturer15', 'lecturer15@example.com', 'password123', '1234567890', 'Hello, I am Lecturer15.', '123 Main St', 1, 1),
    ('Lecturer16', 'lecturer16@example.com', 'password123', '1234567890', 'Hello, I am Lecturer16.', '123 Main St', 1, 1),
    ('Lecturer17', 'lecturer17@example.com', 'password123', '1234567890', 'Hello, I am Lecturer17.', '123 Main St', 1, 1),
    ('Lecturer18', 'lecturer18@example.com', 'password123', '1234567890', 'Hello, I am Lecturer18.', '123 Main St', 1, 1);

-- Mock data for LectureDetails table
INSERT INTO LectureDetails (UserID, LectureDescription, lectureProfile, accepted)
VALUES (3, 'Introduction to Physics', (SELECT * FROM OPENROWSET(BULK 'D:\pic.png', SINGLE_BLOB) AS FileContent), 1),
       (4, 'Algebra Basics', (SELECT * FROM OPENROWSET(BULK 'D:\pic.png', SINGLE_BLOB) AS FileContent), 1);

INSERT INTO LectureDetails (UserID, LectureDescription, lectureProfile, accepted)
VALUES
    (7, 'Introduction to Physics', (SELECT * FROM OPENROWSET(BULK 'D:\pic.png', SINGLE_BLOB) AS FileContent), 1),
    (8, 'Calculus Basics', (SELECT * FROM OPENROWSET(BULK 'D:\pic.png', SINGLE_BLOB) AS FileContent), 1),
    (9, 'Computer Science Fundamentals', (SELECT * FROM OPENROWSET(BULK 'D:\pic.png', SINGLE_BLOB) AS FileContent), 1),
    (10, 'Data Structures and Algorithms', (SELECT * FROM OPENROWSET(BULK 'D:\pic.png', SINGLE_BLOB) AS FileContent), 1),
    (11, 'Introduction to Statistics', (SELECT * FROM OPENROWSET(BULK 'D:\pic.png', SINGLE_BLOB) AS FileContent), 1),
    (12, 'Database Management Systems', (SELECT * FROM OPENROWSET(BULK 'D:\pic.png', SINGLE_BLOB) AS FileContent), 1),
    (13, 'Web Development Fundamentals', (SELECT * FROM OPENROWSET(BULK 'D:\pic.png', SINGLE_BLOB) AS FileContent), 1),
    (14, 'Artificial Intelligence Concepts', (SELECT * FROM OPENROWSET(BULK 'D:\pic.png', SINGLE_BLOB) AS FileContent), 1),
    (15, 'Software Engineering Principles', (SELECT * FROM OPENROWSET(BULK 'D:\pic.png', SINGLE_BLOB) AS FileContent), 1),
    (16, 'Network Security Fundamentals', (SELECT * FROM OPENROWSET(BULK 'D:\pic.png', SINGLE_BLOB) AS FileContent), 1),
    (17, 'Operating System Concepts', (SELECT * FROM OPENROWSET(BULK 'D:\pic.png', SINGLE_BLOB) AS FileContent), 1),
    (18, 'Object-Oriented Programming', (SELECT * FROM OPENROWSET(BULK 'D:\pic.png', SINGLE_BLOB) AS FileContent), 1),
    (19, 'Data Science Fundamentals', (SELECT * FROM OPENROWSET(BULK 'D:\pic.png', SINGLE_BLOB) AS FileContent), 1),
    (20, 'Digital Marketing Strategies', (SELECT * FROM OPENROWSET(BULK 'D:\pic.png', SINGLE_BLOB) AS FileContent), 0),
    (21, 'Business Management Principles', (SELECT * FROM OPENROWSET(BULK 'D:\pic.png', SINGLE_BLOB) AS FileContent), 0);


-- Mock data for Role table
INSERT INTO Role (UserID, UserType)
VALUES (1, 'student'),
       (2, 'admin'),
       (3, 'lecturer'),
       (4, 'lecturer'),
       (5, 'student');

-- Generate userRole for lecturers
INSERT INTO Role (UserID, UserType)
VALUES
    (7, 'lecturer'),
    (8, 'lecturer'),
    (9, 'lecturer'),
    (10, 'lecturer'),
    (11, 'lecturer'),
    (12, 'lecturer'),
    (13, 'lecturer'),
    (14, 'lecturer'),
    (15, 'lecturer'),
    (16, 'lecturer'),
    (17, 'lecturer'),
    (18, 'lecturer'),
    (19, 'lecturer'),
    (20, 'lecturer'),
    (21, 'lecturer'),
    (22, 'lecturer');


-- Generate lecturer reviews for Lecturer 1
INSERT INTO lecture_reviews (lecture_id, user_id, Rating, Comment, review_date)
VALUES
    (1, 1, 4.5, 'Great lecturer!', GETDATE()),
    (1, 5, 4.0, 'Very knowledgeable.', GETDATE());

-- Generate lecturer reviews for Lecturer 2
INSERT INTO lecture_reviews (lecture_id, user_id, Rating, Comment, review_date)
VALUES
    (2, 1, 3.5, 'Average lecturer.', GETDATE()),
    (2, 5, 4.5, 'Explains concepts well.', GETDATE());

-- Generate lecturer reviews for Lecturer 3
INSERT INTO lecture_reviews (lecture_id, user_id, Rating, Comment, review_date)
VALUES
    (3, 1, 4.0, 'Enjoyed the lectures.', GETDATE()),
    (3, 5, 3.5, 'Could be more engaging.', GETDATE());

-- Generate lecturer reviews for Lecturer 4
INSERT INTO lecture_reviews (lecture_id, user_id, Rating, Comment, review_date)
VALUES
    (4, 1, 4.5, 'Very clear explanations.', GETDATE()),
    (4, 5, 4.0, 'Helped me understand complex topics.', GETDATE());

-- Generate lecturer reviews for Lecturer 5
INSERT INTO lecture_reviews (lecture_id, user_id, Rating, Comment, review_date)
VALUES
    (5, 1, 3.5, 'Needs more interactive elements.', GETDATE()),
    (5, 5, 4.5, 'Good introduction to statistics.', GETDATE());

-- Generate lecturer reviews for Lecturer 6
INSERT INTO lecture_reviews (lecture_id, user_id, Rating, Comment, review_date)
VALUES
    (6, 1, 4.0, 'Helped me understand databases better.', GETDATE()),
    (6, 5, 4.0, 'Well-structured content.', GETDATE());

-- Generate lecturer reviews for Lecturer 7
INSERT INTO lecture_reviews (lecture_id, user_id, Rating, Comment, review_date)
VALUES
    (7, 1, 4.5, 'Lectures were engaging.', GETDATE()),
    (7, 5, 3.5, 'Could provide more hands-on exercises.', GETDATE());

-- Generate lecturer reviews for Lecturer 8
INSERT INTO lecture_reviews (lecture_id, user_id, Rating, Comment, review_date)
VALUES
    (8, 1, 4.0, 'Interesting concepts covered.', GETDATE()),
    (8, 5, 4.5, 'Explained topics in an engaging manner.', GETDATE());

-- Generate lecturer reviews for Lecturer 9
INSERT INTO lecture_reviews (lecture_id, user_id, Rating, Comment, review_date)
VALUES
    (9, 1, 3.5, 'Could be more interactive.', GETDATE()),
    (9, 5, 4.0, 'Explained concepts clearly.', GETDATE());

-- Generate lecturer reviews for Lecturer 10
INSERT INTO lecture_reviews (lecture_id, user_id, Rating, Comment, review_date)
VALUES
    (10, 1, 4.5, 'Very knowledgeable lecturer.', GETDATE()),
    (10, 5, 4.0, 'Enjoyed the lectures.', GETDATE());

-- Generate lecturer reviews for Lecturer 11
INSERT INTO lecture_reviews (lecture_id, user_id, Rating, Comment, review_date)
VALUES
    (11, 1, 4.0, 'Engaging teaching style.', GETDATE()),
    (11, 5, 4.5, 'Knowledgeable instructor.', GETDATE());

-- Generate lecturer reviews for Lecturer 12
INSERT INTO lecture_reviews (lecture_id, user_id, Rating, Comment, review_date)
VALUES
    (12, 1, 4.5, 'In-depth coverage of data science concepts.', GETDATE()),
    (12, 5, 3.5, 'Could provide more practical examples.', GETDATE());

-- Generate lecturer reviews for Lecturer 13
INSERT INTO lecture_reviews (lecture_id, user_id, Rating, Comment, review_date)
VALUES
    (13, 1, 4.0, 'Insightful lectures on digital marketing.', GETDATE()),
    (13, 5, 4.0, 'Good understanding of marketing strategies.', GETDATE());

-- Generate lecturer reviews for Lecturer 14
INSERT INTO lecture_reviews (lecture_id, user_id, Rating, Comment, review_date)
VALUES
    (14, 1, 3.5, 'Needs more interactive elements.', GETDATE()),
    (14, 5, 4.5, 'Clear explanations of business management principles.', GETDATE());

-- Generate lecturer reviews for Lecturer 15
INSERT INTO lecture_reviews (lecture_id, user_id, Rating, Comment, review_date)
VALUES
    (15, 1, 4.0, 'Covered a wide range of topics.', GETDATE()),
    (15, 5, 4.0, 'Helped me understand business management concepts.', GETDATE());

-- Generate lecturer reviews for Lecturer 16
INSERT INTO lecture_reviews (lecture_id, user_id, Rating, Comment, review_date)
VALUES
    (16, 1, 4.5, 'Explained physics concepts clearly.', GETDATE()),
    (16, 5, 3.5, 'Covered algebra basics in an easy-to-understand way.', GETDATE());



-- Mock data for Courses table
INSERT INTO Courses (CourseTypeID, CourseDescription, CourseName, LecturerID, Status)
VALUES (1, 'Introduction to Physics', 'Physics 101', 7, 1),
       (2, 'Algebra and Geometry', 'Mathematics 101', 7, 1),
       (3, 'World History', 'History 101', 7, 1),
       (4, 'English Literature', 'Literature 101', 7, 1),
       (5, 'Introduction to Programming', 'Programming 101', 7, 1),
       (1, 'Chemistry Fundamentals', 'Chemistry 101', 1, 1),
       (2, 'Calculus I', 'Mathematics 201', 2, 1),
       (3, 'Ancient Civilizations', 'History 201', 1, 1),
       (4, 'American Literature', 'Literature 201', 2, 1),
       (5, 'Data Structures and Algorithms', 'Programming 201', 1, 1),
       (1, 'Biology Basics', 'Biology 101', 2, 1),
       (2, 'Statistics and Probability', 'Mathematics 301', 1, 1),
       (3, 'Modern World History', 'History 301', 2, 1),
       (4, 'British Literature', 'Literature 301', 1, 1),
       (5, 'Web Development Fundamentals', 'Web Development 101',2, 1),
       (1, 'Environmental Science', 'Environmental Science 101', 1, 1),
       (2, 'Linear Algebra', 'Mathematics 401', 2, 1),
       (3, 'US History', 'History 401', 1, 1),
       (4, 'Shakespearean Literature', 'Literature 401', 2, 1),
       (5, 'Database Management Systems', 'Database Systems 101', 1, 1);
INSERT INTO Courses (CourseTypeID, CourseDescription, CourseName, LecturerID, Status)
VALUES
    -- Course 21-30
    (1, 'Course Description 21', 'Course 21', 1, 1),
    (2, 'Course Description 22', 'Course 22', 2, 1),
    (3, 'Course Description 23', 'Course 23', 3, 1),
    (4, 'Course Description 24', 'Course 24', 4, 1),
    (5, 'Course Description 25', 'Course 25', 5, 1),
    (1, 'Course Description 26', 'Course 26', 6, 1),
    (2, 'Course Description 27', 'Course 27', 7, 1),
    (3, 'Course Description 28', 'Course 28', 8, 1),
    (4, 'Course Description 29', 'Course 29', 9, 1),
    (5, 'Course Description 30', 'Course 30', 10, 1),
    -- Course 31-40
    (1, 'Course Description 31', 'Course 31', 11, 1),
    (2, 'Course Description 32', 'Course 32', 12, 1),
    (3, 'Course Description 33', 'Course 33', 13, 1),
    (4, 'Course Description 34', 'Course 34', 14, 1),
    (5, 'Course Description 35', 'Course 35', 15, 1),
    (1, 'Course Description 36', 'Course 36', 16, 1),
    (2, 'Course Description 37', 'Course 37', 17, 1),
    (3, 'Course Description 38', 'Course 38', 1, 1),
    (4, 'Course Description 39', 'Course 39', 2, 1),
    (5, 'Course Description 40', 'Course 40', 3, 1);
    -- Course 41-50
INSERT INTO Courses (CourseTypeID, CourseDescription, CourseName, LecturerID, Status)
VALUES
    (1, 'Course Description 41', 'Course 41', 4, 1),
    (2, 'Course Description 42', 'Course 42', 5, 1),
    (3, 'Course Description 43', 'Course 43', 6, 1),
    (4, 'Course Description 44', 'Course 44', 7, 1),
    (5, 'Course Description 45', 'Course 45', 8, 1),
    (1, 'Course Description 46', 'Course 46', 9, 1),
    (2, 'Course Description 47', 'Course 47', 10, 1),
    (3, 'Course Description 48', 'Course 48', 11, 1),
    (4, 'Course Description 49', 'Course 49', 12, 1),
    (5, 'Course Description 50', 'Course 50', 13, 1);
    -- Course 51-60

-- Mock data for CourseSection table
INSERT INTO CourseSection (CourseID, SectionName, CreateDate, SectionDuration, SectionNotice)
VALUES (1, 'Introduction', GETDATE(), '01:30:00', 'Please bring your textbooks.'),
       (1, 'Chapter 1', GETDATE(), '01:00:00', 'Review the assigned readings.'),
       (2, 'Algebra Basics', GETDATE(), '01:30:00', 'Practice solving equations.');

-- Mock data for Materials table
INSERT INTO Materials (CourseID, SectionID, MaterialName, MaterialType, MaterialFile)
VALUES (1, 1, 'Introduction Slides', 'Presentation', (SELECT * FROM OPENROWSET(BULK 'D:\pic.png', SINGLE_BLOB) AS FileContent)), -- Insert the binary data for the file
       (1, 2, 'Chapter 1 Notes', 'PDF', (SELECT * FROM OPENROWSET(BULK 'D:\pic.png', SINGLE_BLOB) AS FileContent)); -- Insert the binary data for the file

Select * from quiz
-- Mock data for Quiz table
INSERT INTO Quiz (QuizID, CourseID, SectionID, QuizName, StartTime, Duration, Status)
VALUES ('quiz1', 1, 1, 'Quiz 1', GETDATE(), '00:00:10', 1),
       ('quiz2', 1, 2, 'Quiz 2', GETDATE(), '00:30:00', 1);

-- Mock data for QuizQuestion table
INSERT INTO QuizQuestion (QuestionID, QuizID, QuestionText, Explanation, Picture)
VALUES ('question1', 'quiz1', 'What is the formula for acceleration?', 'Acceleration is calculated as velocity divided by time.', (SELECT * FROM OPENROWSET(BULK 'D:\pic.png', SINGLE_BLOB) AS FileContent)),
       ('question2', 'quiz1', 'Solve the equation: 2x + 5 = 13', 'The solution is x = 4.', (SELECT * FROM OPENROWSET(BULK 'D:\pic.png', SINGLE_BLOB) AS FileContent)),
       ('question3', 'quiz1', 'What is the capital of France?', 'The capital of France is Paris.', NULL),
       ('question4', 'quiz1', 'Who painted the Mona Lisa?', 'The Mona Lisa was painted by Leonardo da Vinci.', NULL ),
       ('question5', 'quiz1', 'What is the square root of 25?', 'The square root of 25 is 5.', NULL),
       ('question6', 'quiz1', 'What is the chemical symbol for gold?', 'The chemical symbol for gold is Au.', NULL),
       ('question7', 'quiz1', 'Who wrote the play "Romeo and Juliet"?', 'The play "Romeo and Juliet" was written by William Shakespeare.', NULL),
       ('question8', 'quiz1', 'What is the largest planet in our solar system?', 'The largest planet in our solar system is Jupiter.', NULL),
       ('question9', 'quiz1', 'What is the formula for the area of a circle?', 'The formula for the area of a circle is a=pi r square', NULL),
       ('question10', 'quiz1', 'Who discovered penicillin?', 'Penicillin was discovered by Alexander Fleming.', NULL),
       ('question11', 'quiz2', 'What is the atomic number of carbon?', 'The atomic number of carbon is 6.', NULL),
       ('question12', 'quiz2', 'Who is known as the "Father of Computer Science"?', 'Alan Turing is known as the "Father of Computer Science".', NULL),
       ('question13', 'quiz2', 'What is the formula for the area of a triangle?', 'The formula for the area of a triangle is A = (base * height) / 2.', NULL),
       ('question14', 'quiz2', 'What is the chemical formula for water?', 'The chemical formula for water is H2O.', NULL),
       ('question15', 'quiz2', 'Who wrote the novel "Pride and Prejudice"?', 'The novel "Pride and Prejudice" was written by Jane Austen.', NULL),
       ('question16', 'quiz2', 'What is the largest organ in the human body?', 'The largest organ in the human body is the skin.', NULL),
       ('question17', 'quiz2', 'What is the formula for the volume of a cylinder?', 'The formula for the volume of a cylinder is V = pi r square h', NULL),
       ('question18', 'quiz2', 'Who is the Greek god of the sea?', 'The Greek god of the sea is Poseidon.', NULL),
       ('question19', 'quiz2', 'What is the formula for the area of a rectangle?', 'The formula for the area of a rectangle is A = length * width.', NULL),
       ('question20', 'quiz2', 'Who wrote the play "Hamlet"?', 'The play "Hamlet" was written by William Shakespeare.', NULL);

-- Mock data for QuizQuestionChoice table
INSERT INTO QuizQuestionChoice (QuestionID, ChoiceText, IsCorrect, Weight)
VALUES ('question1', 'a = v / t', 1, 1.0),
       ('question1', 'a = t / v', 0, 0),
       ('question1', 'a = v + t', 0, 0),
       ('question2', 'x = 6', 0, 0.0),
       ('question2', 'x = 4', 1, 1.0),
       ('question2', 'x = 7', 0, 0.0),
       ('question3', 'London', 0, 0.0),
       ('question3', 'Paris', 1, 1.0),
       ('question3', 'Madrid', 0, 0.0),
       ('question3', 'Berlin', 0, 0.0),
       ('question4', 'Leonardo da Vinci', 1, 1.0),
       ('question4', 'Pablo Picasso', 0, 0.0),
       ('question4', 'Vincent van Gogh', 0, 0.0),
       ('question4', 'Michelangelo', 0, 0.0),
       ('question5', '4', 0, 0.0),
       ('question5', '5', 1, 1.0),
       ('question5', '6', 0, 0.0),
       ('question5', '7', 0, 0.0),
       ('question6', 'Au', 1, 1.0),
       ('question6', 'Ag', 0, 0.0),
       ('question6', 'Fe', 0, 0.0),
       ('question6', 'Na', 0, 0.0),
       ('question7', 'William Shakespeare', 1, 1.0),
       ('question7', 'Charles Dickens', 0, 0.0),
       ('question7', 'Jane Austen', 0, 0.0),
       ('question7', 'Mark Twain', 0, 0.0),
       ('question8', 'Jupiter', 1, 1.0),
       ('question8', 'Mars', 0, 0.0),
       ('question8', 'Saturn', 0, 0.0),
       ('question8', 'Neptune', 0, 0.0),
       ('question9', 'A = πr^2', 1, 1.0),
       ('question9', 'A = 2πr', 0, 0.0),
       ('question9', 'A = πd', 0, 0.0),
       ('question9', 'A = πr^2h', 0, 0.0),
       ('question10', 'Alexander Fleming', 1, 1.0),
       ('question10', 'Isaac Newton', 0, 0.0),
       ('question10', 'Albert Einstein', 0, 0.0),
       ('question10', 'Marie Curie', 0, 0.0),
       ('question11', '6', 1, 1.0),
       ('question11', '7', 0, 0.0),
       ('question11', '8', 0, 0.0),
       ('question11', '9', 0, 0.0),
       ('question12', 'Alan Turing', 1, 1.0),
       ('question12', 'Bill Gates', 0, 0.0),
       ('question12', 'Steve Jobs', 0, 0.0),
       ('question12', 'Mark Zuckerberg', 0, 0.0),
       ('question13', 'A = (base * height) / 2', 1, 1.0),
       ('question13', 'A = length * width', 0, 0.0),
       ('question13', 'A = πr^2', 0, 0.0),
       ('question13', 'A = πr^2h', 0, 0.0),
       ('question14', 'H2O', 1, 1.0),
       ('question14', 'CO2', 0, 0.0),
       ('question14', 'NaCl', 0, 0.0),
       ('question14', 'C6H12O6', 0, 0.0),
       ('question15', 'Jane Austen', 1, 1.0),
       ('question15', 'Charlotte Bronte', 0, 0.0),
       ('question15', 'Emily Dickinson', 0, 0.0),
       ('question15', 'Virginia Woolf', 0, 0.0),
       ('question16', 'Skin', 1, 1.0),
       ('question16', 'Heart', 0, 0.0),
       ('question16', 'Brain', 0, 0.0),
       ('question16', 'Lungs', 0, 0.0),
       ('question17', 'V = πr^2h', 1, 1.0),
       ('question17', 'V = πr^2', 0, 0.0),
       ('question17', 'V = (base * height) / 2', 0, 0.0),
       ('question17', 'V = length * width', 0, 0.0),
       ('question18', 'Poseidon', 1, 1.0),
       ('question18', 'Zeus', 0, 0.0),
       ('question18', 'Hades', 0, 0.0),
       ('question18', 'Apollo', 0, 0.0),
       ('question19', 'A = length * width', 1, 1.0),
       ('question19', 'A = (base * height) / 2', 0, 0.0),
       ('question19', 'A = πr^2', 0, 0.0),
       ('question19', 'A = πr^2h', 0, 0.0),
       ('question20', 'William Shakespeare', 1, 1.0),
       ('question20', 'Charles Dickens', 0, 0.0),
       ('question20', 'Jane Austen', 0, 0.0),
       ('question20', 'Mark Twain', 0, 0.0);

-- Mock data for Reviews table
INSERT INTO Reviews (CourseID, UserID, Rating, Comment, ReviewDate)
VALUES
    -- Course 1
    (1, 1, 4.5, 'Great course!', GETDATE()),
    (1, 2, 3.0, 'The lectures were too fast.', GETDATE()),
    (1, 3, 4.2, 'Excellent content!', GETDATE()),
    (1, 4, 3.8, 'Could be more interactive.', GETDATE()),

    -- Course 2
    (2, 1, 4.0, 'I learned a lot!', GETDATE()),
    (2, 2, 3.5, 'Good course material.', GETDATE()),
    (2, 3, 4.3, 'Enjoyed the assignments.', GETDATE()),
    (2, 4, 3.7, 'Some topics were confusing.', GETDATE()),

    -- Course 3
    (3, 1, 3.8, 'Informative lectures.', GETDATE()),
    (3, 2, 4.1, 'Practical examples were helpful.', GETDATE()),
    (3, 3, 3.6, 'Could improve the pacing.', GETDATE()),
    (3, 4, 4.0, 'Would recommend to others.', GETDATE()),

    -- Course 4
    (4, 1, 4.2, 'Engaging and interactive.', GETDATE()),
    (4, 2, 3.9, 'Good mix of theory and practice.', GETDATE()),
    (4, 3, 4.4, 'Well-structured course.', GETDATE()),
    (4, 4, 3.7, 'Could provide more exercises.', GETDATE()),

    -- Course 5
    (5, 1, 3.6, 'Comprehensive content.', GETDATE()),
    (5, 2, 4.3, 'Helped me understand complex concepts.', GETDATE()),
    (5, 3, 3.8, 'Engaging instructor.', GETDATE()),
    (5, 4, 4.1, 'Practical examples were beneficial.', GETDATE()),

    -- Course 6
    (6, 1, 4.4, 'Great instructor!', GETDATE()),
    (6, 2, 3.7, 'Could improve course materials.', GETDATE()),
    (6, 3, 4.0, 'Good pace and explanations.', GETDATE()),
    (6, 4, 3.9, 'Interesting assignments.', GETDATE()),

    -- Course 7
    (7, 1, 3.9, 'Well-organized course.', GETDATE()),
    (7, 2, 4.2, 'Practical exercises were valuable.', GETDATE()),
    (7, 3, 3.7, 'Could provide more examples.', GETDATE()),
    (7, 4, 4.1, 'Helped me expand my knowledge.', GETDATE()),

    -- Course 8
    (8, 1, 4.1, 'Good instructor communication.', GETDATE()),
    (8, 2, 3.8, 'Could provide more challenging exercises.', GETDATE()),
    (8, 3, 4.3, 'Engaging lectures.', GETDATE()),
    -- Course 9
    (9, 1, 3.7, 'Interesting and informative content.', GETDATE()),
    (9, 2, 4.0, 'Well-structured course material.', GETDATE()),
    (9, 3, 4.2, 'Engaging instructor.', GETDATE()),
    (9, 4, 3.9, 'Could provide more real-world examples.', GETDATE()),

    -- Course 10
    (10, 1, 4.3, 'Practical assignments.', GETDATE()),
    (10, 2, 3.9, 'Good explanations of complex topics.', GETDATE()),
    (10, 3, 4.1, 'Helpful course resources.', GETDATE()),
    (10, 4, 3.7, 'Could improve the quiz difficulty.', GETDATE()),

    -- Course 11
    (11, 1, 4.2, 'Beneficial course for beginners.', GETDATE()),
    (11, 2, 3.8, 'Good introduction to the topic.', GETDATE()),
    (11, 3, 4.3, 'Engaging instructor.', GETDATE()),
    (11, 4, 3.9, 'Could provide more practical examples.', GETDATE()),

    -- Course 12
    (12, 1, 3.9, 'Informative lectures.', GETDATE()),
    (12, 2, 4.1, 'Good course organization.', GETDATE()),
    (12, 3, 3.7, 'Could provide more challenging exercises.', GETDATE()),
    (12, 4, 4.0, 'Helped me understand complex concepts.', GETDATE()),

  -- ... Inserts for courses 13-19 ...

  -- Course 20
  (20, 1, 4.7, 'Outstanding course!', GETDATE()),
  (20, 2, 3.9, 'Some sections were challenging.', GETDATE()),
  (20, 3, 4.4, 'Interactive quizzes were fun.', GETDATE()),
  (20, 4, 4.2, 'Well-structured and engaging.', GETDATE());


-- Mock data for Notice table
INSERT INTO Notice (CourseID, UserID, Notice, NoticeDate)
VALUES (1, 3, 'There will be no class next week.', GETDATE()),
       (2, 3, 'Remember to bring your calculators for the exam.', GETDATE());

-- Mock data for LectureReviews table
INSERT INTO lecture_reviews (lecture_id, user_id, Rating, Comment, review_date)
VALUES (3, 1, 4.5, 'The instructor was very knowledgeable.', GETDATE()),
       (3, 2, 3.0, 'The lecture content was too advanced for me.', GETDATE());

-- Mock data for StudentCourseEnrollment table for student user id of 1 and 5
INSERT INTO StudentCourseEnrollment (CourseID, StudentID, AssignmentDate)
VALUES (1, 1, GETDATE()),
       (2, 1, GETDATE()),
       (3, 1, GETDATE()),
       (4, 1, GETDATE()),
       (5, 1, GETDATE()),
       (6, 1, GETDATE()),
       (7, 1, GETDATE()),
       (8, 1, GETDATE()),
       (9, 1, GETDATE()),
       (10, 1, GETDATE()),
       (11, 1, GETDATE()),
       (12, 1, GETDATE()),
       (13, 1, GETDATE()),
       (14, 1, GETDATE()),
       (15, 1, GETDATE()),
       (16, 1, GETDATE()),
       (17, 1, GETDATE()),
       (18, 1, GETDATE()),
       (19, 1, GETDATE()),
       (20, 1, GETDATE()),
       (1, 5, GETDATE()),
       (2, 5, GETDATE()),
       (3, 5, GETDATE()),
       (4, 5, GETDATE()),
       (5, 5, GETDATE()),
       (6, 5, GETDATE()),
       (7, 5, GETDATE()),
       (8, 5, GETDATE()),
       (9, 5, GETDATE()),
       (10, 5, GETDATE()),
       (11, 5, GETDATE()),
       (12, 5, GETDATE()),
       (13, 5, GETDATE()),
       (14, 5, GETDATE()),
       (15, 5, GETDATE()),
       (16, 5, GETDATE()),
       (17, 5, GETDATE()),
       (18, 5, GETDATE()),
       (19, 5, GETDATE()),
       (20, 5, GETDATE());

-- Mock data for lecture_reviews table
INSERT INTO lecture_reviews (lecture_id, user_id, rating, comment, review_date)
VALUES (3, 1, 4.5, 'The lecture was informative.', GETDATE()),
       (3, 2, 3.0, 'I found it difficult to follow.', GETDATE());

-- Mock data for system_reviews table
INSERT INTO system_reviews (user_id, rating, comment, review_date)
VALUES (1, 4.0, 'The platform is user-friendly.', GETDATE()),
       (2, 4.5, 'I enjoy using this learning system.', GETDATE());

INSERT INTO system_reviews (user_id, rating, comment, review_date)
VALUES
    (1, 4.5, 'The system is absolutely fantastic! It has exceeded my expectations in every way. The user interface is intuitive and user-friendly, making it easy to navigate and access all the features. The system provides a wide range of useful functionalities that have greatly improved my workflow. The performance is outstanding, ensuring fast and efficient processing. Additionally, the customer support team has been incredibly helpful and responsive, addressing any concerns or inquiries promptly. Overall, I am extremely satisfied with the system and would highly recommend it to others.', GETDATE()),
    (5, 4.0, 'I have been using the system for a while now, and it has proven to be an invaluable tool. The system offers a comprehensive set of features that have greatly facilitated my work. The documentation provided is clear and comprehensive, making it easy to understand and utilize the system effectively. The system interface is responsive and well-designed, providing a smooth user experience. While there are some areas where I believe the system could be further improved, such as offering more customization options, it remains a reliable and efficient platform. Overall, I am quite impressed with the system and find it to be a valuable asset in my daily work.', GETDATE());
INSERT INTO system_reviews (user_id, rating, comment, review_date)
VALUES
    (1, 4.5, 'The system is absolutely fantastic! It has exceeded my expectations in every way. The user interface is intuitive and user-friendly, making it easy to navigate and access all the features. The system provides a wide range of useful functionalities that have greatly improved my workflow. The performance is outstanding, ensuring fast and efficient processing. Additionally, the customer support team has been incredibly helpful and responsive, addressing any concerns or inquiries promptly. Overall, I am extremely satisfied with the system and would highly recommend it to others.', GETDATE()),
    (5, 4.0, 'I have been using the system for a while now, and it has proven to be an invaluable tool. The system offers a comprehensive set of features that have greatly facilitated my work. The documentation provided is clear and comprehensive, making it easy to understand and utilize the system effectively. The system interface is responsive and well-designed, providing a smooth user experience. While there are some areas where I believe the system could be further improved, such as offering more customization options, it remains a reliable and efficient platform. Overall, I am quite impressed with the system and find it to be a valuable asset in my daily work.', GETDATE()),
    (2, 4.2, 'I have recently started using the system, and so far, it has been a great addition to my workflow. The system offers a wide range of features that have improved my productivity and efficiency. The user interface is visually appealing and easy to navigate. The system performance is commendable, and I have not experienced any major issues or glitches. The system support team has been responsive and helpful in addressing my inquiries. Overall, I am satisfied with the system and its capabilities.', GETDATE()),
    (3, 3.8, 'The system provides a solid foundation for managing various tasks efficiently. The user interface is straightforward, and it doesn''t take long to get familiar with the system. It offers a good range of features that cover essential functionalities. However, there are a few areas where the system could be improved. For instance, the system response time can be slow at times, especially when dealing with larger datasets. Additionally, the documentation could be more detailed and comprehensive. Despite these drawbacks, the system has been reasonably reliable, and with some enhancements, it could become an even more valuable tool.', GETDATE()),
    (4, 4.5, 'I have been using the system extensively for my projects, and it has proven to be a reliable and efficient platform. The system offers a comprehensive set of tools and features that cater to different needs. The user interface is well-designed and intuitive, enabling seamless navigation. The system performance is commendable, providing quick response times even with complex operations. The system''s flexibility allows customization, which is a significant advantage. The support team has been responsive and knowledgeable in addressing any concerns. Overall, I highly recommend the system for its capabilities and reliability.', GETDATE());