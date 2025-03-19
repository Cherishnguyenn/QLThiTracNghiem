USE ql_thitracnghiem;

CREATE TABLE Classes (
    ClassID VARCHAR(255) PRIMARY KEY,
    ClassName VARCHAR(255) NOT NULL,
    TeacherID INT NOT NULL
);

CREATE TABLE Users (
    UserID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(255) NOT NULL,
    Email VARCHAR(255) UNIQUE NOT NULL,
    Password VARCHAR(255) NOT NULL,
    Role ENUM('Teacher', 'Student') NOT NULL,
    ClassID VARCHAR(255),
    FOREIGN KEY (ClassID) REFERENCES Classes(ClassID)
);

ALTER TABLE Classes
ADD CONSTRAINT FK_Teacher
FOREIGN KEY (TeacherID) REFERENCES Users(UserID);


CREATE TABLE Exams (
    ExamID INT AUTO_INCREMENT PRIMARY KEY,
    ClassID VARCHAR(255) NOT NULL,
    ExamName VARCHAR(255) NOT NULL,
    ExamDate DATETIME NOT NULL,
    FOREIGN KEY (ClassID) REFERENCES Classes(ClassID)
);

CREATE TABLE QuestionBank (
    QuestionID INT AUTO_INCREMENT PRIMARY KEY,
    QuestionText TEXT NOT NULL,
    Subject VARCHAR(255) NOT NULL,
    ClassLevel VARCHAR(50),
    Difficulty ENUM('Easy', 'Medium', 'Hard') NOT NULL,
    QuestionType ENUM('Multiple Choice', 'Essay') NOT NULL
);

CREATE TABLE Answers (
    AnswerID INT AUTO_INCREMENT PRIMARY KEY,
    QuestionID INT NOT NULL,
    AnswerText VARCHAR(255) NOT NULL,
    IsCorrect BOOLEAN NOT NULL,
    FOREIGN KEY (QuestionID) REFERENCES QuestionBank(QuestionID)
);

CREATE TABLE ExamQuestions (
    ExamID INT NOT NULL,
    QuestionID INT NOT NULL,
    PRIMARY KEY (ExamID, QuestionID),
    FOREIGN KEY (ExamID) REFERENCES Exams(ExamID),
    FOREIGN KEY (QuestionID) REFERENCES QuestionBank(QuestionID)
);

CREATE TABLE StudentSubmissions (
    SubmissionID INT AUTO_INCREMENT PRIMARY KEY,
    StudentID INT NOT NULL,
    ExamID INT NOT NULL,
    StartTime DATETIME NOT NULL,
    EndTime DATETIME NOT NULL,
    Score INT NOT NULL,
    CorrectAnswers INT NOT NULL,
    TotalQuestions INT NOT NULL,
    FOREIGN KEY (StudentID) REFERENCES Users(UserID),
    FOREIGN KEY (ExamID) REFERENCES Exams(ExamID)
);

CREATE TABLE StudentAnswers (
    StudentAnswerID INT AUTO_INCREMENT PRIMARY KEY,
    SubmissionID INT NOT NULL,
    QuestionID INT NOT NULL,
    AnswerID INT NOT NULL,
    FOREIGN KEY (SubmissionID) REFERENCES StudentSubmissions(SubmissionID),
    FOREIGN KEY (QuestionID) REFERENCES QuestionBank(QuestionID),
    FOREIGN KEY (AnswerID) REFERENCES Answers(AnswerID)
);

/* Bảng Users
Công dụng:
Lưu trữ thông tin của tất cả người dùng (giáo viên và học sinh).

Phân biệt vai trò (giáo viên hay học sinh) qua cột Role.

Cách hoạt động:
Khi một người dùng mới đăng ký, thông tin của họ được thêm vào bảng này.

Dựa vào vai trò (Teacher hoặc Student), hệ thống xác định quyền truy cập. Ví dụ:

Teacher: Có thể tạo lớp (Classes), bài thi (Exams).

Student: Có thể tham gia lớp và làm bài thi.

Classes
Công dụng:
Quản lý thông tin về các lớp học mà giáo viên tạo ra.

Liên kết giữa học sinh và giáo viên thông qua UserID.

Cách hoạt động:
Khi một giáo viên tạo lớp mới, hệ thống thêm bản ghi vào bảng này với TeacherID là ID của giáo viên.

Học sinh có thể được gắn vào lớp qua bảng Users (ClassID).

Bảng Exams
Công dụng:
Lưu thông tin về các bài thi do giáo viên tạo.

Mỗi bài thi thuộc về một lớp cụ thể.

Cách hoạt động:
Giáo viên tạo bài thi và chỉ định nó cho lớp của họ (ClassID).

Mỗi bài thi có tên (ExamName) và thời gian tổ chức (ExamDate).

QuestionBank
Công dụng:
Lưu trữ tất cả các câu hỏi trong ngân hàng đề. Các câu hỏi này có thể được sử dụng lại cho nhiều bài thi.

Cách hoạt động:
Giáo viên thêm các câu hỏi mới vào ngân hàng đề.

Khi tạo bài thi, hệ thống lấy câu hỏi từ ngân hàng đề qua bảng ExamQuestions.

Answers
Công dụng:
Lưu các đáp án của từng câu hỏi (áp dụng cho dạng câu hỏi trắc nghiệm).

Cách hoạt động:
Mỗi câu hỏi trong bảng QuestionBank có một hoặc nhiều đáp án.

Hệ thống so sánh đáp án do học sinh chọn (từ StudentAnswers) với cột IsCorrect để xác định đúng hay sai.

ExamQuestions
Công dụng:
Là bảng trung gian, liên kết các câu hỏi trong ngân hàng đề (QuestionBank) với bài thi (Exams).

Cách hoạt động:
Khi giáo viên tạo bài thi, họ chọn các câu hỏi từ ngân hàng đề và bảng này lưu thông tin đó.

Một câu hỏi có thể được dùng trong nhiều bài thi khác nhau.

StudentSubmissions
Công dụng:
Lưu tổng hợp kết quả bài làm của học sinh.

Theo dõi thời gian làm bài, điểm số, và số câu trả lời đúng.

Cách hoạt động:
Khi học sinh nộp bài thi, hệ thống tính điểm và lưu dữ liệu vào bảng này.

Giáo viên có thể sử dụng bảng này để xem tổng quan kết quả của học sinh trong mỗi bài thi.

Bảng StudentAnswers 
Công dụng:
Lưu chi tiết từng câu trả lời của học sinh trong bài thi.

Cách hoạt động:
Khi học sinh chọn câu trả lời, hệ thống lưu dữ liệu này vào bảng.

Dữ liệu từ bảng này được sử dụng để tính điểm (đối chiếu với đáp án đúng từ bảng Answers).
*/



