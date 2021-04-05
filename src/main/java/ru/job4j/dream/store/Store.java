package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.util.Collection;

public interface Store {

    static Store instOf() {
        return null;
    }

    Collection<Post> findAllPosts();

    Collection<Candidate> findAllCandidates();

    Post save(Post post);

    Candidate save(Candidate candidate);

    Post findPostById(int id);

    Candidate findCandidateById(int id);

    void deleteCandidateById(int id);

    User save(User user);

    void deleteUserById(int id);

    User findUserByEmail(String email);

    Collection<User> findAllUsers();
}