package ru.job4j.dream.servlet;

import ru.job4j.dream.model.Post;
import ru.job4j.dream.store.PsqlStore;
import ru.job4j.dream.store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * The class saves a post to the store by serving POST-requests
 * and returns all post and forwards to the post.jsp page by serving GET-requests.
 * @author AndrewMs
 * @version 1.0
 */
public class PostServlet extends HttpServlet {
    private final Store store = PsqlStore.instOf();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        store.save(
                new Post(
                Integer.parseInt(req.getParameter("id")),
                req.getParameter("name"), ""
                )
        );
        resp.sendRedirect(req.getContextPath() + "/posts.do");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("posts", store.findAllPosts());
        req.getRequestDispatcher("posts.jsp").forward(req, resp);
    }
}