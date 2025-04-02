package quizmanagementsystem.DTO;

public class StudentScoreDTO {
    private int examID;
    private double score;

    

    public StudentScoreDTO() {
    }

    public StudentScoreDTO(int examID, double score) {
        this.examID = examID;
        this.score = score;
    }

    public int getExamID() {
        return examID;
    }

    public void setExamID(int examID) {
        this.examID = examID;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
