package DTO;

public class AnswerDTO {
    private int answerID;
    private int questionID;
    private String answerText;
    private boolean isCorrect;

   
    public AnswerDTO() {
    }

    public AnswerDTO(int answerID, int questionID, String answerText, boolean isCorrect) {
        this.answerID = answerID;
        this.questionID = questionID;
        this.answerText = answerText;
        this.isCorrect = isCorrect;
    }

  
    public int getAnswerID() {
        return answerID;
    }

    public void setAnswerID(int answerID) {
        this.answerID = answerID;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    @Override
    public String toString() {
        return "AnswerDTO{" +
                "answerID=" + answerID +
                ", questionID=" + questionID +
                ", answerText='" + answerText + '\'' +
                ", isCorrect=" + isCorrect +
                '}';
    }
}
