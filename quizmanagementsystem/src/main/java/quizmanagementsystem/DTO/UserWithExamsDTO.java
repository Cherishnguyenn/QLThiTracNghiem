/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quizmanagementsystem.DTO;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
// UserWithExamsDTO.java
public class UserWithExamsDTO {
    private int userId;
    private String userName;
    private String email;
    private String role;
    private List<ExamWithUserAnswersDTO> examHistory;

    // Constructors
    public UserWithExamsDTO() {
        examHistory = new ArrayList<>();
    }

    // Getters and Setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public List<ExamWithUserAnswersDTO> getExamHistory() { return examHistory; }
    public void setExamHistory(List<ExamWithUserAnswersDTO> examHistory) { 
        this.examHistory = examHistory; 
    }

    public void addExam(ExamWithUserAnswersDTO exam) {
        examHistory.add(exam);
    }
}