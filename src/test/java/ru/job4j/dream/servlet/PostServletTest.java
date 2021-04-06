package ru.job4j.dream.servlet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ru.job4j.dream.model.Post;
import ru.job4j.dream.store.MemStore;
import ru.job4j.dream.store.PsqlStore;
import ru.job4j.dream.store.Store;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PsqlStore.class)
public class PostServletTest {

    @Test
    public void whenAddPostAndStoreIt() throws IOException {
        Store store = MemStore.instOf();
        PowerMockito.mockStatic(PsqlStore.class);
        Mockito.when(PsqlStore.instOf()).thenReturn(store);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getParameter("name")).thenReturn("Post for saving");
        when(req.getParameter("id")).thenReturn("0");
        new PostServlet().doPost(req, resp);
        assertThat(store.findPostById(4).getName(), is("Post for saving"));
    }

    @Test
    public void whenUpdatePost() throws IOException {
        Store store = MemStore.instOf();
        PowerMockito.mockStatic(PsqlStore.class);
        Mockito.when(PsqlStore.instOf()).thenReturn(store);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getParameter("name")).thenReturn("Post for updating");
        when(req.getParameter("id")).thenReturn("3");
        assertThat(store.findPostById(3).getName(), is("Senior Java Job"));
        new PostServlet().doPost(req, resp);
        assertThat(store.findPostById(3).getName(), is("Post for updating"));
    }

    @Test
    public void whenGetAllPosts() throws ServletException, IOException {
        Store store = MemStore.instOf();
        PowerMockito.mockStatic(PsqlStore.class);
        Mockito.when(PsqlStore.instOf()).thenReturn(store);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        Map<String, Collection<Post>> attributes = new HashMap<>();
        Mockito.doAnswer((Answer<Void>) invocation -> {
            String key = invocation.getArgument(0);
            Collection<Post> value = invocation.getArgument(1);
            attributes.put(key, value);
            return null;
        }).when(req).setAttribute(Mockito.anyString(), Mockito.anyCollection());
        Mockito.doAnswer((Answer<Collection<Post>>) invocation -> {
            String key = invocation.getArgument(0);
            return attributes.get(key);
        }).when(req).getAttribute(Mockito.anyString());
        RequestDispatcher rDisp = mock(RequestDispatcher.class);
        when(req.getRequestDispatcher("posts.jsp")).thenReturn(rDisp);
        List<Post> expectedPosts = List.of(
                new Post(1, "Junior Java Job", ""),
                new Post(2, "Middle Java Job", ""),
                new Post(3, "Senior Java Job", "")
        );
        new PostServlet().doGet(req, resp);
        Iterator<Post> postsIt = ((Collection<Post>) req.getAttribute("posts")).iterator();
        assertThat(postsIt.next(), is(expectedPosts.get(0)));
        assertThat(postsIt.next(), is(expectedPosts.get(1)));
        assertThat(postsIt.next(), is(expectedPosts.get(2)));
    }
}