package com.mrahmed.HRandPayrollManagementSystem.entity;

//import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.util.List;
//
//@Entity
//@Table(name = "departments")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class Department {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;
//    private String name;
//    private int employeesNum;
//    private String photo;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "branchId")
//    private Branch branch;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "companyId")
//    private Company company;
//
//    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
//    @JsonIgnore
//    private List<User> users;
//
//}
