package ru.fmt.university.model.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "students")
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupEntity group;

    public StudentEntity(String firstName, String lastName, GroupEntity group) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.group = group;
    }

    public StudentEntity(int id, String firstName, String lastName, GroupEntity group) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.group = group;
    }

    public StudentEntity(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public StudentEntity(int id) {
        this.id = id;
    }

    public StudentEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public GroupEntity getGroup() {
        return group;
    }

    public void setGroup(GroupEntity group) {
        this.group = group;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentEntity studentEntity = (StudentEntity) o;
        return id == studentEntity.id && Objects.equals(firstName, studentEntity.firstName) && Objects.equals(lastName, studentEntity.lastName) && Objects.equals(group, studentEntity.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }
}
