package ru.fmt.university.model.entity;

import javax.persistence.*;
import lombok.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
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
