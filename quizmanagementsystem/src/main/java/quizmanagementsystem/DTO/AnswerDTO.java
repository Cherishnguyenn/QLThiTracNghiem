/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quizmanagementsystem.DTO;

/**
 *
 * @author Admin
 */
public class AnswerDTO {
    private int answerId;
    private int questionId;
    private String answerText;
    private boolean correct;

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }
    
    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }
    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
    
    // Getters and setters

    public AnswerDTO(int answerId, String answerText, boolean correct) {
        this.answerId = answerId;
        this.questionId = questionId;
        this.answerText = answerText;
        this.correct = correct;
    }

    public AnswerDTO() {
    }
}
