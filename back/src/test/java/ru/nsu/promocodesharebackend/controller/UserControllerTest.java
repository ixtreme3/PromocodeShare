package ru.nsu.promocodesharebackend.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import ru.nsu.promocodesharebackend.model.User;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ContextConfiguration(initializers = { UserControllerTest.Initializer.class} )
@Testcontainers
class UserControllerTest {
    @Autowired
    private UserController userController;

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres").
                    withDatabaseName("ps-test-database").
                    withUsername("ps-username").
                    withPassword("ps-password").
                    withInitScript("script.sql");

    @BeforeAll
    public static void setUp() {
        postgreSQLContainer.start();
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize (ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword())
                    .applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Test
    public void createNewUserTest() {
        User user1 = createUser(1L, "User1", "@user1");
        userController.addNewUser(user1);
        long count = userController.userRepository.count();
        assertEquals(1, count);
    }

    @Test
    public void getUsersTest() {
        User user1 = createUser(1L, "User1", "@user1");
        userController.addNewUser(user1);
        List<User> users = userController.findAll();
        String[] actualUsers = { "User1" };
        assertArrayEquals(actualUsers, users.stream().map(user -> user.getName()).toArray());
    }

    @Test
    public void updateUserTest() {
        User newUser1 = createUser(1L, "NewUser1", "@user1");
        userController.updateUser(newUser1);
        List<User> users = userController.findAll();
        assertEquals("NewUser1", users.get(0).getName());
    }

    @Test
    public void deleteUserTest() {
        userController.deleteUser(createUser(1L, "NewUser1", "@user1"));
        long count = userController.userRepository.count();
        assertEquals(0, count);
    }

    @AfterAll
    public static void tearDown() {
        postgreSQLContainer.stop();
    }

    private User createUser(long id, String name, String vkLink) {
        User newUser = new User();
        newUser.setId(id);
        newUser.setName(name);
        newUser.setVkLink(vkLink);
        return newUser;
    }
}