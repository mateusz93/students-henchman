package model;

import javax.persistence.*;

/**
 * Created by Michał on 2016-10-28.
 */
@Entity
@Table(name = "SPECIALIZATION")
public class Specialization {

    private Long id;
    private String name;
    private Field field;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FIELD_ID", nullable = false)
    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }
}
