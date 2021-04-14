package ru.job4j.dream.servlet;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.dream.model.User;
import ru.job4j.dream.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

/**
 * The class verifies user authorization by serving POST-requests or
 * makes users unauthorized by serving GET-requests.
 * It also generates a token (JWT) for using with AJAX CORS-requests on clients.
 * @author AndrewMs
 * @version 1.0
 */
public class AuthServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(AuthServlet.class.getName());
    private String key;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User user = PsqlStore.instOf().findUserByEmail(email.toLowerCase());
        if (user != null && password.equals(user.getPassword())) {
            HttpSession sc = req.getSession();
            sc.setAttribute("user", user);
            sc.setAttribute("token", getToken(user));
            resp.sendRedirect(req.getContextPath() + "/posts.do");
        } else {
            req.setAttribute("error", "Неверный email или пароль");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (req.getSession().getAttribute("user") != null) {
            req.getSession().setAttribute("user", null);
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }

    private String getToken(User user) {
        String token = null;
        try {
            token = JWT.create()
                    .withIssuer("dreamjob")
                    .withClaim("user", user.getId())
                    .withIssuedAt(new Date(System.currentTimeMillis()))
                    .sign(Algorithm.HMAC256(this.key));
        } catch (JWTCreationException e) {
            LOG.error("Exception occurred: ", e);
        }
        return token;
    }

    @Override
    public void init() {
        this.key = getServletContext().getInitParameter("key");
    }
}
