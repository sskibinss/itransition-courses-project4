package com.itransition.courses.project4;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTests {

    @Autowired
    private UserRepository repository;

    @Autowired
    private TestEntityManager entityManager;
    private final String dateTimePattern = "yyyy-MM-dd HH:mm:ss";
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimePattern);

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setEmail("skibin12124123@gmail.com");
        user.setPassword("pass");
        user.setFirstName("Simone");
        user.setLastName("Skybyne");
        user.setRegistrationDate(LocalDate.now().toString());
        user.setLastLoginDate(dateTimeFormatter.format(LocalDateTime.now()));
        user.setStatus(UserStatus.ACTIVE);

        User savedUser = repository.save(user);
        User existUser = entityManager.find(User.class, savedUser.getId());

        assertEquals(existUser.getEmail(), user.getEmail());
    }

    @Test
    public void testFindUserByEmail() {
        String email = "faceskibin991@gmail.com";
        String nonexistentEmail = "sjaglneljafnea@gmail.com";
        User existUser = repository.findByEmail(email);
        User nonexistentUser = repository.findByEmail(nonexistentEmail);
        assertNotNull(existUser);
        assertNull(nonexistentUser);
    }
}
