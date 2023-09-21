package com.medilabo.microservice_patient.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity(name = "Patient")
@Table(name = "patient")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patient_sequence")
    @SequenceGenerator(allocationSize = 1, name = "patient_sequence")
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String phone;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;
}
