package com.example.dublinparkswebapp;

import com.example.dublinparkswebapp.user.User;
import com.example.dublinparkswebapp.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testAddNew() {


        User user = new User();
        user.setEmail("conorm1998@gmail.com");
        user.setPassword("password");
        user.setFirstName("Conor");
        user.setLastName("Mongan");

        User savedUser = userRepository.save(user);



        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);

    }

    @Test
    public void testListAll() {

        Iterable<User> users = userRepository.findAll();
        Assertions.assertThat(users).hasSizeGreaterThan(0);

        for (User user : users) {

            System.out.println(user);
        }

    }

    @Test
    public void testUpdate() {

        Integer userId = 1;
        Optional<User> optionalUser = userRepository.findById(userId);
        User user = optionalUser.get();

        user.setPassword("hello2000");
        userRepository.save(user);

        User updatedUser = userRepository.findById(userId).get();
        Assertions.assertThat(updatedUser.getPassword()).isEqualTo("hello2000");


    }



            @Test
    public void testGet() {
        Integer userId = 2;

                Optional<User> optionalUser = userRepository.findById(userId);
                User user = optionalUser.get();
                Assertions.assertThat(optionalUser).isPresent();
                System.out.println(optionalUser.get());


    }
    @Test
    public void testDelete() {
        Integer userId = 1;
userRepository.deleteById(userId);
        Optional<User> optionalUser = userRepository.findById(userId);
        Assertions.assertThat(optionalUser).isNotPresent();



    }


            }





