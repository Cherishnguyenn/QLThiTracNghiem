create database ql_thitracnghiem;
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
    Role ENUM('Teacher', 'Student','admin') NOT NULL,
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
    QuestionType ENUM('Vocabulary', 'Grammar', 'Tenses', 'Phrases and Idioms') NOT NULL
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

INSERT INTO Users (Name, Email, Password, Role, ClassID)
VALUES 
('Nguyen Van A', 'nguyenvana@example.com', '123', 'Teacher', NULL),
('Tran Thi B', 'tranthib@example.com', '123', 'Student', NULL),
('Le Van C', 'levanc@example.com', '123', 'Student', NULL),
('Pham Minh D', 'phamminhd@example.com', '123', 'admin', NULL),
('Hoang Anh E', 'hoanganhe@example.com', '123', 'Teacher', NULL);

INSERT INTO QuestionBank (QuestionText, QuestionType)
VALUES 
('What is the synonym of "happy"?', 'Vocabulary'),
('What is the antonym of "big"?','Vocabulary'),
('What does the word "enormous" mean?', 'Vocabulary'),
('Choose the correct word to complete: "He is very _______ in his studies."', 'Vocabulary'),
('Which of these means "to improve"?', 'Vocabulary'),
('Which word is a synonym for "fast"?', 'Vocabulary'),
('What is the meaning of "ancient"?', 'Vocabulary'),
('What is another word for "beautiful"?', 'Vocabulary'),
('Which word means "to run away quickly"?', 'Vocabulary'),
('What is the opposite of "strong"?', 'Vocabulary');

-- Thêm câu trả lời
INSERT INTO Answers (QuestionID, AnswerText, IsCorrect)
VALUES 
(1, 'Sad', FALSE), (1, 'Cheerful', TRUE), (1, 'Angry', FALSE), (1, 'Tired', FALSE),
(2, 'Small', TRUE), (2, 'Huge', FALSE), (2, 'Loud', FALSE), (2, 'Bright', FALSE),
(3, 'Tiny', FALSE), (3, 'Enormous', TRUE), (3, 'Quick', FALSE), (3, 'Soft', FALSE),
(4, 'Serious', TRUE), (4, 'Lazy', FALSE), (4, 'Careless', FALSE), (4, 'Angry', FALSE),
(5, 'Enhance', TRUE), (5, 'Break', FALSE), (5, 'Stop', FALSE), (5, 'Lower', FALSE),
(6, 'Quick', TRUE), (6, 'Slow', FALSE), (6, 'Old', FALSE), (6, 'Bright', FALSE),
(7, 'Ancient', TRUE), (7, 'Modern', FALSE), (7, 'Weak', FALSE), (7, 'Soft', FALSE),
(8, 'Beautiful', TRUE), (8, 'Ugly', FALSE), (8, 'Weak', FALSE), (8, 'Quick', FALSE),
(9, 'Escape', TRUE), (9, 'Stay', FALSE), (9, 'Fight', FALSE), (9, 'Eat', FALSE),
(10, 'Weak', TRUE), (10, 'Strong', FALSE), (10, 'Bright', FALSE), (10, 'Huge', FALSE);

INSERT INTO QuestionBank (QuestionText, QuestionType)
VALUES 
('Which is the correct sentence?', 'Grammar'),
('Identify the subject of this sentence: "She sings beautifully."', 'Grammar'),
('Choose the correct form: "He _______ to school every day."', 'Grammar'),
('Which word is a conjunction?', 'Grammar'),
('What is the plural of "mouse"?', 'Grammar'),
('Which sentence uses the past tense?', 'Grammar'),
('Choose the correct article: "I saw _______ apple."','Grammar'),
('Find the adjective in this sentence: "The tall tree is beautiful."', 'Grammar'),
('What part of speech is "quickly" in this sentence: "He runs quickly."', 'Grammar'),
('Choose the correct auxiliary verb: "She _______ working on her project."', 'Grammar');

INSERT INTO Answers (QuestionID, AnswerText, IsCorrect)
VALUES 
(11, 'She go to school.', FALSE), (11, 'She goes to school.', TRUE), (11, 'She going to school.', FALSE), (11, 'She went to school.', FALSE),
(12, 'She', TRUE), (12, 'Sings', FALSE), (12, 'Beautifully', FALSE), (12, 'None', FALSE),
(13, 'Goes', TRUE), (13, 'Go', FALSE), (13, 'Went', FALSE), (13, 'Going', FALSE),
(14, 'And', TRUE), (14, 'Run', FALSE), (14, 'Beautiful', FALSE), (14, 'House', FALSE),
(15, 'Mouses', FALSE), (15, 'Mouse', FALSE), (15, 'Mice', TRUE), (15, 'Mices', FALSE),
(16, 'She is singing.', FALSE), (16, 'She sang a song.', TRUE), (16, 'She sings beautifully.', FALSE), (16, 'She sings every day.', FALSE),
(17, 'An', TRUE), (17, 'A', FALSE), (17, 'The', FALSE), (17, 'None', FALSE),
(18, 'Tall', TRUE), (18, 'Tree', FALSE), (18, 'Is', FALSE), (18, 'Beautiful', FALSE),
(19, 'Quickly', TRUE), (19, 'Runs', FALSE), (19, 'He', FALSE), (19, 'None', FALSE),
(20, 'Is', TRUE), (20, 'Are', FALSE), (20, 'Were', FALSE), (20, 'Has', FALSE);

INSERT INTO QuestionBank (QuestionText, QuestionType)
VALUES 
('What tense is this sentence: "I have lived here for 5 years."', 'Tenses'),
('Choose the past simple form: "He _______ (run) yesterday."', 'Tenses'),
('Which sentence is in the future tense?','Tenses'),
('What tense is used in this sentence: "She was reading a book."', 'Tenses'),
('Find the present continuous sentence.', 'Tenses'),
('Complete this sentence in past perfect: "I _______ (finish) my homework before dinner."', 'Tenses'),
('Identify the present simple sentence.', 'Tenses'),
('Choose the past perfect continuous tense.','Tenses'),
('Fill the blank: "They _______ (play) football tomorrow."', 'Tenses'),
('Which tense describes an ongoing action in the past?', 'Tenses');

INSERT INTO Answers (QuestionID, AnswerText, IsCorrect)
VALUES 
(21, 'Present Perfect', TRUE), 
(21, 'Past Simple', FALSE), 
(21, 'Future Simple', FALSE), 
(21, 'Present Simple', FALSE),
(22, 'Ran', TRUE), 
(22, 'Runs', FALSE), 
(22, 'Run', FALSE), 
(22, 'Running', FALSE),
(23, 'He will go to school.', TRUE), 
(23, 'He goes to school.', FALSE), 
(23, 'He went to school.', FALSE), 
(23, 'He is going to school.', FALSE),
(24, 'Past Continuous', TRUE), 
(24, 'Past Perfect', FALSE), 
(24, 'Present Continuous', FALSE), 
(24, 'Future Continuous', FALSE),
(25, 'She is reading a book.', TRUE), 
(25, 'She read a book.', FALSE), 
(25, 'She has read a book.', FALSE), 
(25, 'She reads a book.', FALSE),
(26, 'Had finished', TRUE), 
(26, 'Finished', FALSE), 
(26, 'Has finished', FALSE), 
(26, 'Finishing', FALSE),
(27, 'She goes to school every day.', TRUE), 
(27, 'She is going to school.', FALSE), 
(27, 'She went to school.', FALSE), 
(27, 'She has gone to school.', FALSE),
(28, 'Had been working', TRUE), 
(28, 'Was working', FALSE), 
(28, 'Has been working', FALSE), 
(28, 'Will have been working', FALSE),
(29, 'Will play', TRUE), 
(29, 'Plays', FALSE), 
(29, 'Played', FALSE), 
(29, 'Playing', FALSE),
(30, 'Past Continuous', TRUE), 
(30, 'Past Perfect', FALSE), 
(30, 'Present Continuous', FALSE), 
(30, 'Future Continuous', FALSE);

INSERT INTO QuestionBank (QuestionText, QuestionType)
VALUES 
('What does the idiom "break the ice" mean?', 'Phrases and Idioms'),
('What does the idiom "hit the books" mean?', 'Phrases and Idioms'),
('What does the idiom "piece of cake" mean?', 'Phrases and Idioms'),
('What does the idiom "burn the midnight oil" mean?', 'Phrases and Idioms'),
('What does the idiom "spill the beans" mean?','Phrases and Idioms'),
('What does the idiom "under the weather" mean?', 'Phrases and Idioms'),
('What does the idiom "bite the bullet" mean?', 'Phrases and Idioms'),
('What does the idiom "cost an arm and a leg" mean?', 'Phrases and Idioms'),
('What does the idiom "cry over spilt milk" mean?', 'Phrases and Idioms'),
('What does the idiom "a blessing in disguise" mean?', 'Phrases and Idioms');

INSERT INTO Answers (QuestionID, AnswerText, IsCorrect)
VALUES 
(31, 'To start a conversation', TRUE), 
(31, 'To destroy something', FALSE), 
(31, 'To feel nervous', FALSE), 
(31, 'To apologize', FALSE),
(32, 'To study hard', TRUE), 
(32, 'To play sports', FALSE), 
(32, 'To hit someone', FALSE), 
(32, 'To read a book', FALSE),
(33, 'Something very easy', TRUE), 
(33, 'Something very expensive', FALSE), 
(33, 'A dangerous situation', FALSE), 
(33, 'A confusing task', FALSE),
(34, 'To work late at night', TRUE), 
(34, 'To burn a book', FALSE), 
(34, 'To avoid sleeping', FALSE), 
(34, 'To destroy evidence', FALSE),
(35, 'To reveal a secret', TRUE), 
(35, 'To drop something', FALSE), 
(35, 'To cook beans', FALSE), 
(35, 'To forget a task', FALSE),
(36, 'To feel unwell', TRUE), 
(36, 'To be outside', FALSE), 
(36, 'To feel happy', FALSE), 
(36, 'To be angry', FALSE),
(37, 'To face a difficult situation bravely', TRUE), 
(37, 'To bite something', FALSE), 
(37, 'To give up', FALSE), 
(37, 'To avoid a problem', FALSE),
(38, 'Something very expensive', TRUE), 
(38, 'Something very heavy', FALSE), 
(38, 'Something very easy', FALSE), 
(38, 'Something very old', FALSE),
(39, 'To waste time worrying about something that has already happened', TRUE), 
(39, 'To spill milk', FALSE), 
(39, 'To fix a mistake', FALSE), 
(39, 'To clean up a mess', FALSE),
(40, 'A good thing that seemed bad at first', TRUE), 
(40, 'A bad thing that seems good', FALSE), 
(40, 'A confusing situation', FALSE), 
(40, 'A simple solution', FALSE);
