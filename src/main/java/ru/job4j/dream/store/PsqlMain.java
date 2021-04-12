package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.util.Collection;
/**
 * The class for testing stores.
 * @author AndrewMs
 * @version 1.0
 */
public class PsqlMain {
    public static void main(String[] args) {
        Store store = PsqlStore.instOf();
        User user1 = new User(0, "Vladimir", "v@ya.ru", "12345");
        User user2 = new User(0, "Ivan", "i@mail.ru", "67890");
        user1 = store.save(user1);
        user2 = store.save(user2);
        System.out.println("All users:");
        for (User user : store.findAllUsers()) {
            System.out.println(user.getId() + " "
                    + user.getName() + " " + user.getEmail() + " " + user.getPassword());
        }
        user1.setName("Vladimir Petrov");
        store.save(user1);
        User foundUser = store.findUserByEmail("v@ya.ru");
        System.out.println("Found user: "
                + foundUser.getId() + " " + foundUser.getName()
                + " " + foundUser.getEmail() + " " + foundUser.getPassword());
        store.deleteUserById(2);
        System.out.println("All users:");
        for (User user : store.findAllUsers()) {
            System.out.println(user.getId() + " "
                    + user.getName() + " " + user.getEmail() + " " + user.getPassword());
        }
    }
}
