use DummiesCourse

SELECT * from Users

-- Drop existing tables]

DROP TABLE IF EXISTS LectureReviews
DROP TABLE IF EXISTS lecture_reviews;
DROP TABLE IF EXISTS system_reviews;
DROP TABLE IF EXISTS StudentCourseEnrollment;
DROP TABLE IF EXISTS LectureDetails;
DROP TABLE IF EXISTS Notice;
DROP TABLE IF EXISTS Reviews;
DROP TABLE IF EXISTS QuizResultAnswer;
DROP TABLE IF EXISTS QuizResultQuestion;
DROP TABLE IF EXISTS QuizResult;
DROP TABLE IF EXISTS QuizQuestionChoice;
DROP TABLE IF EXISTS QuizQuestion;
DROP TABLE IF EXISTS QuizLabel;
DROP TABLE IF EXISTS Quiz;
DROP TABLE IF EXISTS Materials;
DROP TABLE IF EXISTS CourseSection;
DROP TABLE IF EXISTS Courses;
DROP TABLE IF EXISTS Role;
DROP TABLE IF EXISTS CourseType;
DROP TABLE IF EXISTS Users;

CREATE TABLE CourseType
(
    CourseTypeID INT IDENTITY(1,1) PRIMARY KEY,
    TypeName VARCHAR(50)
)


CREATE TABLE Users
(
    UserID INT IDENTITY(1,1) PRIMARY KEY,
    Username VARCHAR(50),
    Email VARCHAR(100),
    Password VARCHAR(100),
    PhoneNumber VARCHAR(10),
    bio varchar(1000),
    Address VARCHAR(50),
    Gender INT,
    Status INT
)

CREATE TABLE Role
(
    RoleID INT IDENTITY(1,1) PRIMARY KEY,
    UserID INT,
    UserType VARCHAR(50),
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
)

CREATE TABLE Courses
(
    CourseID INT IDENTITY(1,1) PRIMARY KEY,
    CourseTypeID INT,
    CourseDescription VARCHAR(255),
    CourseName VARCHAR(100),
    LecturerID INT,
    Status INT,
    FOREIGN KEY (LecturerID) REFERENCES Users(UserID),
    FOREIGN KEY (CourseTypeID) REFERENCES CourseType(CourseTypeID)
)

CREATE TABLE CourseSection
(
    SectionID INT IDENTITY(1,1) PRIMARY KEY,
    CourseID INT,
    SectionName VARCHAR(100),
    CreateDate DATETIME,
    SectionDuration TIME,
    SectionNotice VARCHAR(255),
    FOREIGN KEY (CourseID) REFERENCES Courses(CourseID)
)

CREATE TABLE Materials
(
    MaterialID INT IDENTITY(1,1) PRIMARY KEY,
    CourseID INT,
    SectionID INT,
    MaterialName VARCHAR(100),
    MaterialType VARCHAR(50),
    MaterialFile VARBINARY(MAX),
    FOREIGN KEY (CourseID) REFERENCES Courses(CourseID),
    FOREIGN KEY (SectionID) REFERENCES CourseSection(SectionID)
)

CREATE TABLE Quiz
(
    QuizID varchar(32) PRIMARY KEY,
    CourseID INT,
    SectionID INT,
    QuizName VARCHAR(100),
    StartTime DATETIME,
    Duration TIME,
    Status INT,
    FOREIGN KEY (CourseID) REFERENCES Courses(CourseID),
    FOREIGN KEY (SectionID) REFERENCES CourseSection(SectionID)
)

CREATE TABLE QuizQuestion
(
    QuestionID varchar(32) PRIMARY KEY,
    QuizID varchar(32),
    QuestionText VARCHAR(255),
    Explanation VARCHAR(255),
    Picture VARBINARY(MAX),
    FOREIGN KEY (QuizID) REFERENCES Quiz(QuizID),
)

CREATE TABLE QuizQuestionChoice
(
    ChoiceID INT IDENTITY(1,1) PRIMARY KEY,
    QuestionID varchar(32),
    ChoiceText VARCHAR(255),
    IsCorrect BIT,
    Weight FLOAT,
    FOREIGN KEY (QuestionID) REFERENCES QuizQuestion(QuestionID)
)

CREATE TABLE QuizResult
(
    ResultID varchar(32) PRIMARY KEY,
    QuizID varchar(32),
    StudentID INT,
    Score FLOAT,
    TestTime DATETIME,
    FOREIGN KEY (StudentID) REFERENCES Users(UserID)
)

CREATE TABLE QuizResultQuestion
(
    ResultQuestionID varchar(32) PRIMARY KEY,
    ResultID varchar(32),
    QuestionID varchar(32),
    point float,
)

CREATE TABLE QuizResultAnswer
(
    ResultChoiceID INT IDENTITY(1,1) PRIMARY KEY,
    ResultQuestionID varchar(32),
    ChoiceID INT
)

CREATE TABLE Reviews
(
    ReviewID INT IDENTITY(1,1) PRIMARY KEY,
    CourseID INT,
    UserID INT,
    Rating FLOAT,
    Comment VARCHAR(255),
    ReviewDate DATETIME,
    FOREIGN KEY (CourseID) REFERENCES Courses(CourseID),
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
)

CREATE TABLE Notice
(
    NoticeID INT IDENTITY(1,1) PRIMARY KEY,
    CourseID INT,
    UserID INT,
    Notice VARCHAR(2000),
    NoticeDate DATETIME,
    FOREIGN KEY (CourseID) REFERENCES Courses(CourseID),
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
)


CREATE TABLE StudentCourseEnrollment
(
    AssignmentID INT IDENTITY(1,1) PRIMARY KEY,
    CourseID INT,
    StudentID INT,
    AssignmentDate DATETIME,
    FOREIGN KEY (CourseID) REFERENCES Courses(CourseID),
    FOREIGN KEY (StudentID) REFERENCES Users(UserID)
)



CREATE TABLE system_reviews
(
    review_id INT IDENTITY(1,1) PRIMARY KEY,
    user_id INT,
    rating FLOAT,
    comment VARCHAR(1000),
    review_date DATETIME,
    FOREIGN KEY (user_id) REFERENCES Users(UserID)
)

CREATE TABLE LectureDetails
(
    LectureID          INT IDENTITY (1,1) PRIMARY KEY,
    UserID             INT,
    LectureDescription VARCHAR(255),
    lectureProfile VARBINARY(MAX),
    accepted BIT,
    FOREIGN KEY (UserID) REFERENCES Users (UserID)
)

CREATE TABLE lecture_reviews
(
    review_id INT IDENTITY(1,1) PRIMARY KEY,
    lecture_id INT,
    user_id INT,
    rating FLOAT,
    comment VARCHAR(255),
    review_date DATETIME,
    FOREIGN KEY (lecture_id) REFERENCES LectureDetails(LectureID),
    FOREIGN KEY (user_id) REFERENCES Users(UserID)
)