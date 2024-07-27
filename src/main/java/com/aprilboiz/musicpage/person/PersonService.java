package com.aprilboiz.musicpage.person;

import com.aprilboiz.musicpage.user.User;

public interface PersonService {
    Person findPersonByUser(User user);
}
