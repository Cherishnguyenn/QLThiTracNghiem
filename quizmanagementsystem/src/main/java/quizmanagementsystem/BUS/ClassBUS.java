package quizmanagementsystem.BUS;

import quizmanagementsystem.DAO.ClassDAO;
import quizmanagementsystem.DTO.ClassDTO;

import java.util.List;
import java.util.Random;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class ClassBUS {

    // Hàm sinh mã lớp không bị trùng
    public static String generateClassCode() {
        Random rand = new Random();
        String classID;
        boolean exists;

        do {
            classID = "CLS" + (10000 + rand.nextInt(90000)); // Tạo mã CLSXXXXX
            exists = ClassDAO.checkClassCodeExists(classID);
        } while (exists); // Nếu trùng thì sinh lại

        return classID;
    }

    // Gọi hàm thêm lớp từ DAO
    public static boolean addClass(String className, int teacherID) {
        String classID = generateClassCode(); // Tạo mã lớp mới
        return ClassDAO.insertClass(classID, className, teacherID);
    }

    // Gọi hàm lấy danh sách lớp từ DAO
    public static List<ClassDTO> getAllClasses() {
        return ClassDAO.getAllClasses(); // Lấy dữ liệu từ database
    }


    // Hàm cập nhật tên lớp
    public static boolean updateClassName(String classID, String newClassName) {
        return ClassDAO.updateClassName(classID, newClassName);
    }

    //Hàm xóa lớp
    public static boolean deleteClass(String classID){
        return ClassDAO.deleteClass(classID);
    }
    //Hàm sắp xếp tăng dần
    public static List<ClassDTO> sortClasses(boolean ascending) {
        List<ClassDTO> classList = getAllClasses(); // Lấy danh sách lớp từ DAO
    
        classList.sort((class1, class2) -> {
            if (ascending) {
                return class1.getClassName().compareTo(class2.getClassName());
            } else {
                return class2.getClassName().compareTo(class1.getClassName());
            }
        });
    
        return classList;
    }
   // Phương thức tìm kiếm lớp
   public static List<ClassDTO> searchClasses(String keyword) {
    return ClassDAO.searchClassByName(keyword); // Gọi DAO để lấy danh sách lớp
}

// Phương thức cập nhật dữ liệu tìm kiếm
public static void searchClassData(String keyword, DefaultTableModel tableModel) {
    List<ClassDTO> classList = searchClasses(keyword);
    
    // Xóa dữ liệu cũ trên bảng
    tableModel.setRowCount(0);

    // Đổ dữ liệu mới vào bảng
    for (ClassDTO classObj : classList) {
        Vector<Object> row = new Vector<>();
        row.add(classObj.getClassID());
        row.add(classObj.getClassName());
        row.add(classObj.getTeacherID());
        tableModel.addRow(row);
    }
}
    
}
