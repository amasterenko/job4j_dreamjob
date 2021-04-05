package ru.job4j.dream.servlet;

import ru.job4j.dream.model.User;
import ru.job4j.dream.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RegServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        if (name.length() == 0 || email.length() == 0 || password.length() == 0) {
            req.setAttribute("error", "Пустое поле!");
            req.getRequestDispatcher("reg.jsp").forward(req, resp);
            return;
        }
        User user = PsqlStore.instOf().save(new User(0, name, email.toLowerCase(), password));
        if (user == null) {
            req.setAttribute("error", "Указанный email уже существует!");
            req.getRequestDispatcher("reg.jsp").forward(req, resp);
            return;
        }
        resp.sendRedirect(req.getContextPath() + "/login.jsp");
    }

}
