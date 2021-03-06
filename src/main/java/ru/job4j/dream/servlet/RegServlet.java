package ru.job4j.dream.servlet;

import ru.job4j.dream.model.User;
import ru.job4j.dream.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * The class saves a user with specified fields to the store.
 * It checks the user's email for uniqueness.
 * @author AndrewMs
 * @version 1.0
 */
public class RegServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User user = PsqlStore.instOf().findUserByEmail(email.toLowerCase());
        if (user != null) {
            req.setAttribute("error", "Указанный email уже существует!");
            req.getRequestDispatcher("reg.jsp").forward(req, resp);
            return;
        }
        PsqlStore.instOf().save(new User(0, name, email.toLowerCase(), password));
        resp.sendRedirect(req.getContextPath() + "/login.jsp");
    }

}
