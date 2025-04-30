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
public class QuestionWithUserAnswersDTO {
    private int questionId;
    private String questionText;

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

    public List<AnswerWithSelectionDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerWithSelectionDTO> answers) {
        this.answers = answers;
    }
    private String questionType;
    private List<AnswerWithSelectionDTO> answers;

    public QuestionWithUserAnswersDTO(int questionId, String questionText, String questionType, List<AnswerWithSelectionDTO> answers) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.questionType = questionType;
        this.answers = answers;
    }
    
    // Getters and setters

    public QuestionWithUserAnswersDTO() {
    }
}