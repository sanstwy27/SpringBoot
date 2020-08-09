package com.atkjs927.springboot.entity;


import javax.persistence.*;

// JPA Configuration
@Entity // Entity-Mapping Class
@Table(name = "tbl_user") // @Table set Table, default table is class name
public class User {

    @Id // Key
    @GeneratedValue(strategy = GenerationType.IDENTITY)// AutoInc.
    private Integer id;

    @Column(name = "last_name",length = 50) // Mapping to Column
    private String lastName;

    @Column // Default Param Name is Column Name
    private String email;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
