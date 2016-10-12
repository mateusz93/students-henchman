package cdm;

import model.Teacher;

import java.util.List;

/**
 * @Author Mateusz Wieczorek on 10/12/16.
 */
public class TeacherRS {

    List<Teacher> teachers;

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    @Override
    public String toString() {
        return "TeacherRS{" +
                "teachers=" + teachers +
                '}';
    }
}
