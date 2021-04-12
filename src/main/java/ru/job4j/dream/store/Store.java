package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.City;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.util.Collection;
/**
 * The interface represents a store for posts, candidates and users.
 * @author AndrewMs
 * @version 1.0
 */
public interface Store {

    static Store instOf() {
        return null;
    }

    Post save(Post post);

    Collection<Post> findAllPosts();

    Post findPostById(int id);

    void deleteCandidateById(int id);

    Candidate save(Candidate candidate);

    Candidate findCandidateById(int id);

    Collection<Candidate> findAllCandidates();

    City findCityById(int id);

    Collection<City> findAllCities();

    User save(User user);

    void deleteUserById(int id);

    User findUserByEmail(String email);

    Collection<User> findAllUsers();
}