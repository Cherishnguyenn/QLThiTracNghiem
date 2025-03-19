package quizmanagementsystem;

import quizmanagementsystem.GUI.AdminFrame;
import quizmanagementsystem.GUI.LoginGUI;
// import quizmanagementsystem.DTO.LoginDTO;
// import quizmanagementsystem.BUS.LoginBUS;
import quizmanagementsystem.GUI.StudentFrame;
import quizmanagementsystem.GUI.TeacherFrame;

public class Main {
    public static void main(String[] args) {
        new LoginGUI();
        new AdminFrame();
        new StudentFrame();
        new TeacherFrame();
    }
}