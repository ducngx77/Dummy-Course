-- function to get the number of QuizResult records in the last N hours
use DummiesCourse
CREATE FUNCTION GetQuizResultCountInLastNHours (@nHours INT)
    RETURNS TABLE
        AS
        RETURN
        SELECT COUNT(*) AS QuizResultCount
        FROM QuizResult
        WHERE TestTime >= DATEADD(HOUR, -@nHours, GETDATE()) AND TestTime <= GETDATE();

SELECT * from GetQuizResultCountInLastNHours(2);

-- searches for courses by name or lecturer's name, considering a specific course type, with offset and limit

CREATE FUNCTION SearchCourseByTypeWithOffset
(
    @searchTerm NVARCHAR(100),
    @courseTypeID INT,
    @offset INT,
    @limit INT
)
    RETURNS TABLE
        AS
        RETURN
            (
                SELECT c.CourseID, c.CourseName, u.Username AS LecturerName, c.CourseTypeID, c.CourseDescription, c.Status, c.LecturerID
                FROM Courses c
                         INNER JOIN Users u ON c.LecturerID = u.UserID
                         INNER JOIN CourseType ct ON c.CourseTypeID = ct.CourseTypeID
                WHERE (c.CourseName LIKE '%' + @searchTerm + '%' OR u.Username LIKE '%' + @searchTerm + '%')
                  AND ct.CourseTypeID = @courseTypeID
                ORDER BY c.CourseID
                OFFSET @offset ROWS FETCH NEXT @limit ROWS ONLY
            )


SELECT * FROM SearchCourseByTypeWithOffset('', 1, 0, 10)


--searches for courses by name or lecturer with offset and limit
CREATE FUNCTION SearchCoursesWithOffset
(
    @searchTerm VARCHAR(100),
    @offset INT,
    @limit INT
)
    RETURNS TABLE
        AS
        RETURN
            (
                SELECT *
                FROM Courses AS c
                WHERE c.CourseName LIKE '%' + @searchTerm + '%'
                   OR EXISTS (SELECT 1 FROM Users AS u WHERE u.UserID = c.LecturerID AND u.Username LIKE '%' + @searchTerm + '%')
                ORDER BY c.CourseID
                OFFSET @offset ROWS FETCH NEXT @limit ROWS ONLY
            )

SELECT * FROM dbo.SearchCoursesWithOffset('201', 0, 10)


-- get course in a course type  with offset
CREATE FUNCTION GetCoursesByTypeWithOffset
(
    @courseTypeID INT,
    @offset INT,
    @limit INT
)
    RETURNS TABLE
        AS
        RETURN
            (
                SELECT *
                FROM (
                         SELECT c.*,
                                ROW_NUMBER() OVER (ORDER BY c.CourseID) AS RowNum
                         FROM Courses AS c
                         WHERE c.CourseTypeID = @courseTypeID
                     ) AS CourseData
                WHERE RowNum > @offset
                ORDER BY RowNum
                OFFSET 0 ROWS FETCH NEXT @limit ROWS ONLY
            )

SELECT *
FROM dbo.GetCoursesByTypeWithOffset(1, 0, 20)

-- get top 4 lecturers with offset

CREATE FUNCTION GetTopLecturersWithOffset
(
    @offset INT,
    @limit INT
)
    RETURNS TABLE
        AS
        RETURN
            (
                SELECT TOP (@limit) *
                FROM (
                         SELECT u.*, AVG(lr.Rating) AS AverageRating,
                                ROW_NUMBER() OVER (ORDER BY AVG(lr.Rating) DESC) AS RowNum
                         FROM Users AS u
                                  INNER JOIN LectureDetails AS ld ON u.UserID = ld.UserID
                                  LEFT JOIN lecture_reviews AS lr ON ld.LectureID = lr.lecture_id
                         WHERE u.Status = 1 -- Adjust the status value based on your criteria for an active user
                         GROUP BY u.UserID, u.Username, u.Email, u.Password, u.PhoneNumber, u.bio, u.Address, u.Gender, u.Status
                     ) AS LecturerRanking
                WHERE RowNum > @offset
                ORDER BY RowNum
            )

SELECT *
FROM dbo.GetTopLecturersWithOffset(0, 4) -- Offset: 0, Limit: 4


-- Get top 3 courses with offset
CREATE FUNCTION GetTopRatedCoursesWithOffset
(
    @offset INT,
    @limit INT
)
    RETURNS TABLE
        AS
        RETURN
            (
                SELECT *
                FROM (
                         SELECT c.*, AVG(r.Rating) AS AverageRating,
                                ROW_NUMBER() OVER (ORDER BY AVG(r.Rating) DESC) AS RowNum
                         FROM Courses AS c
                                  INNER JOIN Reviews AS r ON c.CourseID = r.CourseID
                         GROUP BY c.CourseID, c.CourseName, c.CourseTypeID, c.CourseDescription, c.LecturerID, c.Status
                     ) AS CourseRanking
                WHERE RowNum > @offset
                ORDER BY RowNum
                OFFSET 0 ROWS
                    FETCH NEXT @limit ROWS ONLY
            )


SELECT *
FROM dbo.GetTopRatedCoursesWithOffset(0, 10) -- Offset: 0, Limit: 10


-- Get top system reviews with offset
CREATE FUNCTION GetTopSystemReviewsWithOffset(@offset INT)
    RETURNS TABLE
        AS
        RETURN
            (
                SELECT *
                FROM
                    (
                        SELECT ROW_NUMBER() OVER (ORDER BY review_id) AS RowNum, *
                        FROM system_reviews
                    ) AS Subquery
                WHERE RowNum > @offset
                ORDER BY RowNum
                OFFSET 0 ROWS
                    FETCH NEXT 10 ROWS ONLY
            );

SELECT * FROM GetTopSystemReviewsWithOffset(0);

-- Get reviews with offset
CREATE FUNCTION GetTopReviewsWithOffset(@offset INT)
    RETURNS TABLE
        AS
        RETURN
            (
                SELECT *
                FROM
                    (
                        SELECT ROW_NUMBER() OVER (ORDER BY ReviewID) AS RowNum, *
                        FROM Reviews
                    ) AS Subquery
                WHERE RowNum > @offset
                ORDER BY RowNum
                OFFSET 0 ROWS
                    FETCH NEXT 3 ROWS ONLY
            );

SELECT * FROM GetTopReviewsWithOffset(0);


-- Create function to get 4 lecturers with offset
CREATE FUNCTION GetLecturersWithOffset(@offset INT)
    RETURNS TABLE
        AS
        RETURN
            (
                WITH LecturerCTE AS (
                    SELECT U.userID, U.Username, U.Email, U.PhoneNumber, U.bio, U.Address, U.Gender, U.Status,
                           ROW_NUMBER() OVER (ORDER BY U.UserID) AS RowNum
                    FROM Users U
                             INNER JOIN Role R ON U.UserID = R.UserID
                    WHERE R.UserType = 'lecturer'
                )
                SELECT UserID, Username, Email, PhoneNumber, bio, Address, Gender, Status
                FROM LecturerCTE
                WHERE RowNum > @offset
                ORDER BY RowNum
                OFFSET 0 ROWS FETCH NEXT 4 ROWS ONLY
            );

SELECT * FROM GetLecturersWithOffset(8);

-- Average rating for a course
CREATE FUNCTION GetAverageRatingByCourseID
(
    @CourseID INT
)
    RETURNS FLOAT
AS
BEGIN
    DECLARE @AverageRating FLOAT;

    SELECT @AverageRating = AVG(Rating)
    FROM Reviews
    WHERE CourseID = @CourseID;

    RETURN @AverageRating;
END;

-- usage example
SELECT dbo.GetAverageRatingByCourseID(8) AS 'Average Rating';


-- TOP rated course with offset function
CREATE FUNCTION dbo.GetTopRatedCourses
(
    @offset INT
)
    RETURNS TABLE
        AS
        RETURN
            (
                SELECT *
                FROM
                    (
                        SELECT
                            c.CourseID,
                            c.CourseName,
                            c.CourseDescription,
                            c.CourseTypeID,
                            c.LecturerID,
                            c.Status,
                            AVG(r.Rating) AS AvgRating,
                            ROW_NUMBER() OVER (ORDER BY AVG(r.Rating) DESC) AS RowNum
                        FROM
                            Courses c
                                INNER JOIN
                            Reviews r ON c.CourseID = r.CourseID
                        GROUP BY
                            c.CourseID,
                            c.CourseName,
                            c.CourseDescription,
                            c.CourseTypeID,
                            c.LecturerID,
                            c.Status
                    ) AS SubQuery
                WHERE RowNum > @offset
                ORDER BY RowNum
                OFFSET 0 ROWS FETCH NEXT 4 ROWS ONLY
            )
-- example
SELECT * FROM dbo.GetTopRatedCourses(0);


-- Create a function to get top 7 registered courses based on reviews for a user
CREATE FUNCTION dbo.GetTopCoursesByUserReviews
(
    @UserID INT
)
    RETURNS TABLE
        AS
        RETURN
            (
                SELECT c.CourseID, c.CourseName, c.CourseDescription, c.CourseTypeID, c.LecturerID, c.Status
                FROM Courses c
                         INNER JOIN (
                    SELECT TOP 7 r.CourseID, AVG(r.Rating) AS AvgRating
                    FROM Reviews r
                             INNER JOIN StudentCourseEnrollment e ON r.CourseID = e.CourseID
                    WHERE e.StudentID = @UserID
                    GROUP BY r.CourseID
                    ORDER BY AvgRating DESC
                ) AS topCourses ON c.CourseID = topCourses.CourseID
            )


-- Usage example: Get top 7 registered courses based on reviews for user with UserID = 1
SELECT *
FROM dbo.GetTopCoursesByUserReviews(1);


--Functions
-- For each functions/view run individual query to create them
-- If the function/view is already created. Change create into alter

 CREATE FUNCTION GetAllResultAnswersByResultId
(
    @resultId varchar(32)
)
RETURNS TABLE
AS
RETURN
(
    SELECT
        qra.ResultChoiceID,
        qra.ResultQuestionID,
        qra.ChoiceID
    FROM
        QuizResultAnswer qra
        INNER JOIN QuizResultQuestion qrq ON qra.ResultQuestionID = qrq.ResultQuestionID
    WHERE
        qrq.ResultID = @resultId
);

 

--SELECT * FROM dbo.GetAllResultAnswersByResultId(2) -- replace 1 with the actual result ID


CREATE FUNCTION GetQuizzesDoneByStudentForTest(@studentID INT, @quizID VARCHAR(32))
RETURNS INT
AS
BEGIN
    DECLARE @quizCount int;

    SELECT @quizCount = COUNT(*) FROM QuizResult
    WHERE StudentID = @studentID
    AND QuizID = @quizID
    AND TestTime >= DATEADD(HOUR, -12, GETDATE());

    RETURN @quizCount;
END;

 

--SELECT dbo.GetQuizzesDoneByStudentForTest(5, 'quiz1') AS QuizzesDone;


CREATE VIEW RandomSystemReviews AS
SELECT TOP 5 review_id, user_id, rating, comment, review_date
FROM system_reviews
ORDER BY NEWID();

 
--SELECT * FROM RandomSystemReviews;


 CREATE FUNCTION dbo.GetCourseReviewAverageScore (@courseID INT)
RETURNS FLOAT
AS
BEGIN
    DECLARE @averageScore FLOAT;

    SELECT @averageScore = AVG(rating)
    FROM Reviews
    WHERE CourseID = @courseID;

    RETURN @averageScore;
END;

 

--SELECT dbo.GetCourseReviewAverageScore(3) as 'Average Score' -- Replace 1 with the desired course ID;

CREATE FUNCTION GetUserBySystemReviewID
(
    @ReviewID INT
)
RETURNS TABLE
AS
RETURN
(
    SELECT Users.UserID, Users.Username, Users.Email
    FROM system_reviews
    INNER JOIN Users ON system_reviews.user_id = Users.UserID
    WHERE system_reviews.review_id = @ReviewID
)



 
-- SELECT * FROM dbo.GetUserBySystemReviewID(2)

CREATE FUNCTION GetAssignedCoursesByTypeAndUser (@typeName VARCHAR(50), @userID INT)
RETURNS TABLE
AS
RETURN
(
    SELECT
        c.CourseID,
        c.CourseName,
        c.CourseDescription,
        c.CourseTypeID,
        c.LecturerID,
        c.Status
    FROM
        Courses c
        INNER JOIN CourseType ct ON c.CourseTypeID = ct.CourseTypeID
        INNER JOIN StudentCourseEnrollment sce ON c.CourseID = sce.CourseID
    WHERE
        ct.TypeName = @typeName
        AND sce.StudentID = @userID
);

 

--SELECT * FROM dbo.GetAssignedCoursesByTypeAndUser('Mathematics', 2) -- Replace 'TypeNameHere' with the desired course type and 1 with the student's user ID



CREATE FUNCTION GetEnrolledCourses (@studentID INT)
RETURNS TABLE
AS
RETURN
(
    SELECT
        c.CourseID,
        c.CourseName,
        c.CourseDescription,
        c.CourseTypeID,
        c.LecturerID,
        c.Status
    FROM
        Courses c
        INNER JOIN StudentCourseEnrollment sce ON c.CourseID = sce.CourseID
    WHERE
        sce.StudentID = @studentID
);


 --SELECT * FROM dbo.GetEnrolledCourses(1)

 


 CREATE FUNCTION GetCoursesByCourseTypeAndLecturerID
(
    @courseTypes VARCHAR(100),
    @lecturerID INT
)
RETURNS TABLE
AS
RETURN
(
    SELECT C.CourseID, C.CourseTypeID, C.CourseDescription, C.CourseName, C.LecturerID, C.Status
    FROM Courses AS C
    INNER JOIN CourseType AS CT ON C.CourseTypeID = CT.CourseTypeID
    WHERE C.LecturerID = @lecturerID
      AND CT.TypeName IN (SELECT value FROM STRING_SPLIT(@courseTypes, ','))
);
 

--SELECT * FROM dbo.GetCoursesByCourseTypeAndLecturerID('Physics', 6);


 CREATE PROCEDURE GetActiveCoursesByLecturerID
    @lecturerID INT
AS
BEGIN
    SELECT C.CourseID, C.CourseTypeID, C.CourseDescription, C.CourseName, C.LecturerID, C.Status
    FROM Courses AS C
    WHERE C.LecturerID = @lecturerID AND C.Status = 1;
END;

 

-- EXEC GetActiveCoursesByLecturerID @lecturerID = 6;


 CREATE FUNCTION GetCoursesByCourseType (@typeName VARCHAR(50))
RETURNS TABLE
AS
RETURN
    SELECT C.CourseID, C.CourseTypeID, C.CourseDescription, C.CourseName, C.LecturerID, C.Status
    FROM Courses AS C
    INNER JOIN CourseType AS CT ON C.CourseTypeID = CT.CourseTypeID
    WHERE CT.TypeName = @typeName;

 

--SELECT * FROM GetCoursesByCourseType('Mathematics');


 CREATE FUNCTION GetUserByReviewID
(
    @ReviewID INT
)
RETURNS TABLE
AS
RETURN
(
    SELECT Users.*
    FROM Users
    INNER JOIN Reviews ON Reviews.UserID = Users.UserID
    WHERE Reviews.ReviewID = @ReviewID
);
 

--SELECT * FROM dbo.GetUserByReviewID(1);


 CREATE FUNCTION GetLecturerInformation (@courseID INT)
    RETURNS TABLE
        AS
        RETURN
            (
                SELECT u.UserID AS LecturerID, u.Username, u.Email, u.PhoneNumber
                FROM Users u
                         INNER JOIN Courses c ON u.UserID = c.LecturerID
                WHERE c.CourseID = @courseID
            );
 

--SELECT * FROM dbo.GetLecturerInformation( 1 );
-- Views

-- View for all lectures
CREATE VIEW AllLectures AS
SELECT u.UserID, u.Username, u.Email, u.PhoneNumber
FROM Users u
JOIN Role r ON u.UserID = r.UserID
WHERE r.UserType = 'lecture'
  AND u.Status = 1; -- Assuming '1' represents the status for active users


--SELECT * FROM AllLectures;

-- View for top 3 rating courses
CREATE VIEW TopRatedCourses AS
SELECT C.CourseID, C.CourseTypeID, C.CourseDescription, C.CourseName, C.LecturerID, C.Status
FROM Courses AS C
JOIN (
    SELECT TOP 3 CourseID, AVG(Rating) AS AverageRating
    FROM Reviews
    GROUP BY CourseID
    ORDER BY AverageRating DESC
) AS Top3RatingCourse ON C.CourseID = Top3RatingCourse.CourseID
WHERE C.Status = 1; -- Assuming '1' represents the status for active courses

