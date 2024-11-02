package com.mrahmed.HRandPayrollManagementSystem.entity;

//import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.util.List;
//
//
//@Entity
//@Table(name = "Companies")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class Company {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String name;
//    private String photo;
//
//    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
//    @JsonIgnore
//    private List<Branch> branches;
//
//    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
//    @JsonIgnore
//    private List<Department> departments;
//
//    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
//    @JsonIgnore
//    private List<User> users;
//
//}
