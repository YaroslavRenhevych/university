package com.yrenh.university.entity;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name = "DEPARTMENT")
public class Department {
    @Id
    @Column(name = "DEPARTMENT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "NAME", unique = true, nullable = false)
    private String name;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "DEPARTMENT_HEAD",
        joinColumns = @JoinColumn(name = "DEPARTMENT_ID"),
        inverseJoinColumns = @JoinColumn(name = "HEAD_ID")
    )
    private Lector head;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "DEPARTMENT_LECTOR",
        joinColumns = {@JoinColumn(name = "DEPARTMENT_ID")},
        inverseJoinColumns = {@JoinColumn(name = "LECTOR_ID")}
    )
    private List<Lector> lectors;

    public Department() {
    }

    public Department(String name, Lector head, List<Lector> lectors) {
        this.name = name;
        this.head = head;
        this.lectors = lectors;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Lector getHead() {
        return head;
    }

    public void setHead(Lector head) {
        this.head = head;
    }

    public List<Lector> getLectors() {
        return lectors;
    }

    public void setLectors(List<Lector> lectors) {
        this.lectors = lectors;
    }
}
