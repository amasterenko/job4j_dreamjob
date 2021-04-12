package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.City;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * The class implements a memory store.
 * @author AndrewMs
 * @version 1.0
 */
public class MemStore implements Store {
    private static final AtomicInteger POST_ID = new AtomicInteger(3);
    private static final AtomicInteger CANDIDATE_ID = new AtomicInteger(3);
    private static final AtomicInteger USER_ID = new AtomicInteger(3);
    private final static MemStore INST = new MemStore();
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    private final Map<Integer, User> users = new ConcurrentHashMap<>();

    private MemStore() {
        City city1 = new City(1, "London");
        City city2 = new City(2, "Moscow");
        City city3 = new City(3, "New York");
        posts.put(1, new Post(1, "Junior Java Job", "description for junior job"));
        posts.put(2, new Post(2, "Middle Java Job", "description for middle job"));
        posts.put(3, new Post(3, "Senior Java Job", "description for senior job"));
        candidates.put(1, new Candidate(1, "Junior Java", city1));
        candidates.put(2, new Candidate(2, "Middle Java", city2));
        candidates.put(3, new Candidate(3, "Senior Java", city3));
    }

    public static MemStore instOf() {
        return INST;
    }

    public Collection<Post> findAllPosts() {
        return posts.values();
    }

    public Collection<Candidate> findAllCandidates() {
        return candidates.values();
    }

    @Override
    public City findCityById(int id) {
        return new City(1, "London");
    }

    @Override
    public Collection<City> findAllCities() {
        return List.of(new City(1, "London"), new City(2, "Moscow"));
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(POST_ID.incrementAndGet());
        }
        posts.put(post.getId(), post);
        return post;
    }

    public Candidate save(Candidate candidate) {
        if (candidate.getId() == 0) {
            candidate.setId(CANDIDATE_ID.incrementAndGet());
        }
        candidates.put(candidate.getId(), candidate);
        return candidate;
    }

    public Post findPostById(int id) {
        return posts.get(id);
    }

    public Candidate findCandidateById(int id) {
        return candidates.get(id);
    }

    @Override
    public void deleteCandidateById(int id) {
        candidates.remove(id);
    }

    @Override
    public User save(User user) {
        if (user.getId() == 0) {
            user.setId(USER_ID.incrementAndGet());
        }
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public void deleteUserById(int id) {
        users.remove(id);
    }

    @Override
    public User findUserByEmail(String email) {
        for (User u : users.values()) {
            if (email.equals(u.getEmail())) {
                return u;
            }
        }
        return null;
    }

    @Override
    public Collection<User> findAllUsers() {
        return users.values();
    }
}
