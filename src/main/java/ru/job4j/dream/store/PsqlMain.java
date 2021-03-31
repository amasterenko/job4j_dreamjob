package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

public class PsqlMain {
    public static void main(String[] args) {
        Store store = PsqlStore.instOf();
        Post post1 = new Post(0, "Java Job", "");
        Post post2 = new Post(0, "PHP Job", "");
        store.save(post1);
        store.save(post2);
        System.out.println("All posts:");
        for (Post post : store.findAllPosts()) {
            System.out.println(post.getId() + " "
                    + post.getName() + " " + post.getCreated());
        }
        post1.setName("Super Job");
        store.save(post1);
        Post foundPost = store.findPostById(1);
        System.out.println("Found post: "
                + foundPost.getId() + " " + foundPost.getName()
                + " " + foundPost.getDescription() + " " + foundPost.getCreated());

        Candidate cand1 = new Candidate(0, "Donald Trump");
        Candidate cand2 = new Candidate(0, "Fedor Konyukhov");
        store.save(cand1);
        store.save(cand2);
        System.out.println("All candidates:");
        for (Candidate candidate : store.findAllCandidates()) {
            System.out.println(candidate.getId() + " " + candidate.getName());
        }
        cand1.setName("Donald Duck");
        store.save(cand1);
        Candidate foundCandidate = store.findCandidateById(1);
        System.out.println("Found candidate: " + foundCandidate.getId()
                + " " + foundCandidate.getName());
    }
}
