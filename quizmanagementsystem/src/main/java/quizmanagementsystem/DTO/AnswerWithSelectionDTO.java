/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quizmanagementsystem.DTO;

/**
 *
 * @author Admin
 */
public class AnswerWithSelectionDTO extends AnswerDTO {
    private boolean selected;

    public AnswerWithSelectionDTO(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    
    // Getters and setters

    public AnswerWithSelectionDTO() {
    }
}