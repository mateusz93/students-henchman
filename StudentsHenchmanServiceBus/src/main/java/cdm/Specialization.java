package cdm;

import java.io.Serializable;

/**
 * @Author Mateusz Wieczorek on 10/6/16.
 */
public class Specialization implements Serializable {

    private int id;
    private String name;
    private Subject subject;
    private Field field;

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    @Override
    public String toString() {
        return "Specialization{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", subject=" + subject +
                ", field=" + field +
                '}';
    }
}