package DTO;

public class ExamsDTO {
    private int examID;
    private String classID;
    private String examName;
    private String examDate;
    private String examTime;
    private String examType;
    private int numberOfQuestions;
    private double totalScore;

    public ExamsDTO() {
    }

    public ExamsDTO(int examID, String classID, String examName, String examDate, String examTime, String examType, int numberOfQuestions, double totalScore) {
        this.examID = examID;
        this.classID = classID;
        this.examName = examName;
        this.examDate = examDate;
        this.examTime = examTime;
        this.examType = examType;
        this.numberOfQuestions = numberOfQuestions;
        this.totalScore = totalScore;
    }

    // Getters and Setters
    public int getExamID() {
        return examID;
    }

    public void setExamID(int examID) {
        this.examID = examID;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public String getExamTime() {
        return examTime;
    }

    public void setExamTime(String examTime) {
        this.examTime = examTime;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }
}
