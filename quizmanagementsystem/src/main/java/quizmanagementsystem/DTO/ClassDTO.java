package quizmanagementsystem.DTO;

public class ClassDTO {
    private String ClassID;
    private String ClassName;
    private int TeacherID;

    public ClassDTO() {
    }

    public ClassDTO(String classID, String className, int teacherID) {
        ClassID = classID;
        ClassName = className;
        TeacherID = teacherID;
    }


    public ClassDTO(String classID, String className) {
        ClassID = classID;
        ClassName = className;
    }

    public void setClassID(String classID) {
        ClassID = classID;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public void setTeacherID(int teacherID) {
        TeacherID = teacherID;
    }

    public String getClassID() {
        return ClassID;
    }

    public String getClassName() {
        return ClassName;
    }

    public int getTeacherID() {
        return TeacherID;
    }
}
