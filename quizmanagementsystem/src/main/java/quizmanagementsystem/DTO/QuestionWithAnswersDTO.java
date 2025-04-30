/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quizmanagementsystem.DTO;

import java.util.List;

/**
 *
 * @author Admin
 */
public class QuestionWithAnswersDTO {
    private int questionId;
    private String questionText;
    private String questionType;
    private List<AnswerDTO> answers;

    public QuestionWithAnswersDTO(int questionId, String questionText, String questionType, List answers) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.questionType = questionType;
        this.answers = answers;
    }
    
    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public List<AnswerDTO> getAnswers() {
        return answers;
    }

    // Getters and setters
    public void setAnswers(List<AnswerDTO> answers) {
        this.answers = answers;
    }

    public QuestionWithAnswersDTO() {
    }
}