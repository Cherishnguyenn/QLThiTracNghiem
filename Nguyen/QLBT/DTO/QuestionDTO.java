package DTO;

import java.util.ArrayList;
import java.util.List;

public class QuestionDTO {
    private int questionID;
    private String questionText;
    private String questionType;
    private List<AnswerDTO> answers; 

    
    public QuestionDTO() {
        this.answers = new ArrayList<>();
    }

    public QuestionDTO(int questionID, String questionText, String questionType) {
        this.questionID = questionID;
        this.questionText = questionText;
        this.questionType = questionType;
        this.answers = new ArrayList<>();
    }

    // Getters and Setters
    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
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

    public void setAnswers(List<AnswerDTO> answers) {
        this.answers = answers;
    }

   
    public void addAnswer(AnswerDTO answer) {
        this.answers.add(answer);
    }

    @Override
    public String toString() {
        return "QuestionDTO{" +
                "questionID=" + questionID +
                ", questionText='" + questionText + '\'' +
                ", questionType='" + questionType + '\'' +
                ", answers=" + answers +
                '}';
    }
}
