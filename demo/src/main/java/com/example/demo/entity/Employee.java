package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employee")
public class Employee implements Serializable {

    @Serial
    private static final long serialVersionUID = -4087427781700397811L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstname" ,updatable = false)
    private String firstName;

    @Column(name = "lastname",updatable = false)
    private String lastName;

    @Column(name = "dateofbirth")
    @Temporal(TemporalType.DATE)//storing just the date part (without the time) in the database
    private LocalDate dateOfBirth;

    @Column(name = "email")
    private String email;

    @Column(name = "gender")
    private String gender;

    @Column(name = "createdby")
    private String createdBy;

    @Column(name = "createddate")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdDate;

    @Column(name = "modifiedby")
    private String modifiedBy;

    @Column(name = "modifieddate")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime modifiedDate;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;

}
