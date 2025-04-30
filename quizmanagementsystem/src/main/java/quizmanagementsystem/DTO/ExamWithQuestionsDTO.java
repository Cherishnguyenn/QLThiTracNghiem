/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quizmanagementsystem.DTO;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ExamWithQuestionsDTO {
    private int examId;
    private String examName;
    private Timestamp examDate;

    public ExamWithQuestionsDTO(int examId, String examName, Timestamp examDate, String examType, Time examTime, List questions) {
        this.examId = examId;
        this.examName = examName;
        this.examDate = examDate;
        this.examType = examType;
        this.examTime = examTime;
        this.questions = questions;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public Timestamp getExamDate() {
        return examDate;
    }

    public void setExamDate(Timestamp examDate) {
        this.examDate = examDate;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public Time getExamTime() {
        return examTime;
    }

    public void setExamTime(Time examTime) {
        this.examTime = examTime;
    }

    public List getQuestions() {
        return questions;
    }

    public void setQuestions(List questions) {
        this.questions = questions;
    }

    public ExamWithQuestionsDTO() {
    }
    private String examType;
    private Time examTime;
    private List<QuestionWithAnswersDTO> questions;
    
    // Getters and setters
}

