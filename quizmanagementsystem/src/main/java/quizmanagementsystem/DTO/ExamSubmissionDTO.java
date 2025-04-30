/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quizmanagementsystem.DTO;

import java.sql.Timestamp;

/**
 *
 * @author Admin
 */
public class ExamSubmissionDTO {
    private int submissionId;
    private int studentId;
    private int examId;
    private Timestamp startTime;
    private Timestamp endTime;
    private double score;
    private int correctAnswers;

    public ExamSubmissionDTO(int submissionId, int studentId, int examId, Timestamp startTime, Timestamp endTime, double score, int correctAnswers, int totalQuestions) {
        this.submissionId = submissionId;
        this.studentId = studentId;
        this.examId = examId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.score = score;
        this.correctAnswers = correctAnswers;
        this.totalQuestions = totalQuestions;
    }

    public int getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(int submissionId) {
        this.submissionId = submissionId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public ExamSubmissionDTO() {
    }
    private int totalQuestions;
    
    // Getters and setters
}