package com.aprilboiz.musicpage.person;

import com.aprilboiz.musicpage.person.dto.PersonResponse;
import com.aprilboiz.musicpage.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "person")
public class Person {
    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Column(unique = true)
    private String email;
    @Column(name = "phone_number", unique = true)
    private String phoneNumber;
    private String address;

    @OneToOne(mappedBy = "person", fetch = FetchType.LAZY)
    private User user;

    public Person(String name, String email, String phoneNumber, String address) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public PersonResponse toDTO(){
        return new PersonResponse(
            this.id,
            this.name,
            this.email,
            this.phoneNumber,
            this.address
        );
    }
}
