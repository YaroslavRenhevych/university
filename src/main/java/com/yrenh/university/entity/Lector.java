package com.yrenh.university.entity;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name = "LECTOR")
public class Lector {
    @Id
    @Column(name = "LECTOR_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "FIRSTNAME", unique = false, nullable = false)
    private String firstName;
    @Column(name = "LASTNAME", unique = false, nullable = false)
    private String lastName;
    @Column(name = "SALARY", nullable = false)
    private Float salary;
    @Enumerated(EnumType.STRING)
    private Degree degree;
    @ManyToMany(mappedBy = "lectors")
    private List<Department> departments;

    public Lector() {
    }

    public Lector(String firstName, String lastName, Float salary, Degree degree, List<Department> departments) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.degree = degree;
        this.departments = departments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    public Degree getDegree() {
        return degree;
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }
}
