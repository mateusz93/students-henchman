package new_model;

import javax.persistence.*;

/**
 * Created by Michał on 2016-10-30.
 */
@Entity
@Table(name = "TEACHER")
public class Teacher {

    private long id;
    private String name;
    private String email;
    //private Set<Course> courses;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", unique = true, nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "NAME", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Column(name = "EMAIL", unique = true, nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

   /* @OneToMany(mappedBy = "teacher", fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }*/
}
