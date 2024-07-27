package com.aprilboiz.musicpage.person;

import com.aprilboiz.musicpage.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long>{
    Optional<Person> findPersonByUser(User user);
}
